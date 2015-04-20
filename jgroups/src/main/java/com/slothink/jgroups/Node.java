package com.slothink.jgroups;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by slothink on 2014-11-19.
 */
public class Node {
    private static final BasicLogger log = Logger.getLogger(Node.class);

    private final boolean useXmlConfig;
    private final String cacheName;
    private final String nodeName;
    private volatile boolean stop = false;

    public Node(boolean useXmlConfig, String cacheName, String nodeName) {
        this.useXmlConfig = useXmlConfig;
        this.cacheName = cacheName;
        this.nodeName = nodeName;
    }

    public static void main(String[] args) throws Exception {
        boolean useXmlConfig = false;
        String cache = "repl";
        String nodeName = null;

        for (String arg : args) {
            if(arg.equals("-x")) {
                useXmlConfig = true;
            }else if(arg.equals("-p")) {
                useXmlConfig = false;
            } else if(arg.equals("-d")) {
                cache = "dist";
            } else if(arg.equals( "-r")) {
                    cache = "repl";
            }else {
                nodeName = arg;
            }
        }
        new Node(useXmlConfig, cache, nodeName).run();
    }

    public void run() throws IOException, InterruptedException {
        EmbeddedCacheManager cacheManager = createCacheManager();
        final Cache<String, String> cache = cacheManager.getCache(cacheName);
        System.out.printf("Cache %s started on %s, cache members are now %s\n", cacheName, cacheManager.getAddress(),
                cache.getAdvancedCache().getRpcManager().getMembers());

        // Add a listener so that we can see the puts to this node
        cache.addListener(new LoggingListener());

        printCacheContents(cache);

        Thread putThread = new Thread() {
            @Override
            public void run() {
                int counter = 0;
                while (!stop) {
                    try {
                        cache.put("key-" + counter, "" + cache.getAdvancedCache().getRpcManager().getAddress() + "-" + counter);
                    } catch (Exception e) {
                        log.warnf("Error inserting key into the cache", e);
                    }
                    counter++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        putThread.start();

        System.out.println("Press Enter to print the cache contents, Ctrl+D/Ctrl+Z to stop.");
        while (System.in.read() > 0) {
            printCacheContents(cache);
        }

        stop = true;
        putThread.join();
        cacheManager.stop();
        System.exit(0);
    }

    /**
     * {@link org.infinispan.Cache#entrySet()}
     */
    private void printCacheContents(Cache<String, String> cache) {
        System.out.printf("Cache contents on node %s\n", cache.getAdvancedCache().getRpcManager().getAddress());

        ArrayList<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(cache.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (Map.Entry<String, String> e : entries) {
            System.out.printf("\t%s = %s\n", e.getKey(), e.getValue());
        }
        System.out.println();
    }

    private EmbeddedCacheManager createCacheManager() throws IOException {
        if (useXmlConfig) {
            return createCacheManagerFromXml();
        } else {
            return createCacheManagerProgrammatically();
        }
    }

    private EmbeddedCacheManager createCacheManagerProgrammatically() {
        System.out.println("Starting a cache manager with a programmatic configuration");
        DefaultCacheManager cacheManager = new DefaultCacheManager(
                GlobalConfigurationBuilder.defaultClusteredBuilder()
                        .transport().nodeName(nodeName).addProperty("configurationFile", "jgroups.xml")
                        .build(),
                new ConfigurationBuilder()
                        .clustering()
                        .cacheMode(CacheMode.REPL_SYNC)
                        .build()
        );
        // The only way to get the "repl" cache to be exactly the same as the default cache is to not define it at all
        cacheManager.defineConfiguration("dist", new ConfigurationBuilder()
                        .clustering()
                        .cacheMode(CacheMode.DIST_SYNC)
                        .hash().numOwners(2)
                        .build()
        );
        return cacheManager;
    }

    private EmbeddedCacheManager createCacheManagerFromXml() throws IOException {
        System.out.println("Starting a cache manager with an XML configuration");
        System.setProperty("nodeName", nodeName);
        return new DefaultCacheManager("infinispan.xml");
    }
}
