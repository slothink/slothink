package com.slothink.jgroups;

import com.comas.solme.framework.util.ResourceUtils;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by slothink on 2014-11-18.
 */
public class SimpleChat2 extends ReceiverAdapter {
    JChannel channel ;
    String username = System.getProperty("user.name", "n/a")+"2";

    private void start() throws Exception {
//        channel = new JChannel(ResourceUtils.getResource("classpath:jgroups.xml").getFile());
        channel = new JChannel();
        channel.connect("ChatCluster");
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
        new SimpleChat2().start();
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: "+new_view);
    }

    @Override
    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": "+ msg.getObject());
    }
}
