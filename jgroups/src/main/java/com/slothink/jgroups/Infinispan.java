package com.slothink.jgroups;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by slothink on 2014-11-18.
 */
public class Infinispan {

    public static void main(String[] args) {
        Cache<Object, Object> cache = new DefaultCacheManager().getCache();
        // Add a entry
        cache.put("key", "value");
// Validate the entry is now in the cache
        assertEquals(1, cache.size());
        assertTrue(cache.containsKey("key"));
// Remove the entry from the cache
        Object v = cache.remove("key");
// Validate the entry is no longer in the cache
        assertEquals("value", v);
    }

    public void test() {
//        org.infinispan.quickstart.clusteredcache.replication.Node0
        String classpath = "jgroups.xml";
    }
}
