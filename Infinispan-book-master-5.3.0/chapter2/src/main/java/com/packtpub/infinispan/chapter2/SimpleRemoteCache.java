package com.packtpub.infinispan.chapter2;

import model.Ticket;
import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import utils.IOUtils;

import java.util.Set;

/**
 * Created by slothink on 2015-04-13.
 */
public class SimpleRemoteCache {
    public void start()  {
        RemoteCacheManager manager = new RemoteCacheManager();
        RemoteCache<Integer, Ticket> cache = manager.getCache();
        String command = ""; // Line read from standard in
        int ticketid = 1;

        IOUtils.dumpWelcomeMessage();

        while (true){
            command = IOUtils.readLine("> ");
            if (command.equals("book")) {

                String name = IOUtils.readLine("Enter name ");
                String show = IOUtils.readLine("Enter show ");

                Ticket ticket = new Ticket(name,show);
                cache.put(ticketid, ticket);
                log("Booked ticket "+ticket);
                ticketid++;
            }
            else if (command.equals("pay")) {
                Integer id = new Integer(IOUtils.readLine("Enter ticketid "));
                Ticket ticket = cache.remove(id);
                log("Checked out ticket "+ticket);
            }
            else if (command.equals("list")) {
                Set<Integer> set = cache.keySet();
                for (Integer ticket: set) {
                    System.out.println(cache.get(ticket));
                }
            }
            else if (command.equals("quit")) {
                cache.stop();
                log("Bye");
                break;
            }
            else {
                log("Unknown command "+command);
                IOUtils.dumpWelcomeMessage();
            }
        }




    }

    public static void main(String[] args) {
        new SimpleRemoteCache().start();

    }
    public static void log(String s){
        System.out.println(s);
    }
}
