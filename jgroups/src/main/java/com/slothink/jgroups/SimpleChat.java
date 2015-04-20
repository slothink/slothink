package com.slothink.jgroups;

import com.comas.solme.framework.util.ResourceUtils;
import org.jgroups.*;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;

import javax.management.MBeanServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by slothink on 2014-11-12.
 */
public class SimpleChat extends ReceiverAdapter implements ChannelListener {
    JChannel channel ;
    String username = System.getProperty("user.name", "n/a") +"1";
    private final String clusterName = "ChatCluster";

    private Option option = new Option();




    private void start() throws Exception {
        channel = new JChannel(ResourceUtils.getResource("classpath:udp_default.xml").getFile());
//        channel = new JChannel();
        channel.setReceiver(this);
        channel.addChannelListener(this);
        if(!option.isNo_channel() && !option.isUse_state()) {
            channel.connect(clusterName);
        }
        System.out.println(channel.getAddressAsString());
        eventLoop();
    }

    private void eventLoop() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if (line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                line = "[" + username + "] " + line;
                Message message = new Message(null, null, line);
                channel.send(message);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: "+new_view);
    }

    @Override
    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": "+ msg.getObject());
    }

    @Override
    public void channelConnected(Channel channel) {
        if(option.isJmx()) {
            Util.registerChannel((JChannel) channel, "jgroups");
        }
    }

    @Override
    public void channelDisconnected(Channel channel) {
        if(option.isJmx()) {
            MBeanServer server=Util.getMBeanServer();
            if(server != null) {
                try {
                    JmxConfigurator.unregisterChannel((JChannel) channel, server, clusterName);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void channelClosed(Channel channel) {

    }
}
