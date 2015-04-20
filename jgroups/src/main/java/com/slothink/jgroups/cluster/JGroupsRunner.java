package com.slothink.jgroups.cluster;

import com.comas.solme.framework.util.ResourceUtils;
import org.jgroups.*;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by slothink on 2015-01-22.
 */
public class JGroupsRunner  extends ReceiverAdapter implements ChannelListener, Runnable {

    private Logger logger = LoggerFactory.getLogger(JGroupsEcmClusterServiceImpl.class);

    JChannel channel ;
    String username = System.getProperty("user.name", "n/a") +"1";

    private Option option = new Option();
    private String clusterName;
    private String jgroupsConfigurationResourcePath;

    private Map<String, ClusterInfo.ClusterNodeInfo> clusterNodes = new LinkedHashMap<String, ClusterInfo.ClusterNodeInfo>();

    public JGroupsRunner(String clusterName, String jgroupsConfigurationResourcePath) {
        this.clusterName = clusterName;
        this.jgroupsConfigurationResourcePath = jgroupsConfigurationResourcePath;
    }

    private void start() throws Exception {
        channel = new JChannel(ResourceUtils.getResource(jgroupsConfigurationResourcePath).getInputStream());
//        channel = new JChannel();
        channel.setReceiver(this);
        channel.addChannelListener(this);
        if(!option.isNo_channel() && !option.isUse_state()) {
            channel.connect(this.clusterName);
        }
        logger.info("Cluster Node Started. Address:{}", new Object[]{channel.getAddressAsString()});
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

    private String extractNodeIdFromView(View view) {
        return Util.printListWithDelimiter(view.getMembers(),", ",Util.MAX_LIST_PRINT_SIZE);
    }

    @Override
    public void viewAccepted(View newView) {
        String nodeId = extractNodeIdFromView(newView);
        ClusterInfo.ClusterNodeInfo nodeInfo = new ClusterInfo.ClusterNodeInfo();
        nodeInfo.setId(nodeId);
        nodeInfo.setAlive(true);
        nodeInfo.setState(EcmServiceState.READY);
        clusterNodes.put(nodeId, nodeInfo);
        logger.info("Cluster Member Joined: {}", new Object[]{nodeId});
    }

    @Override
    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": "+ msg.getObject());
        if(msg.getObject() instanceof ClusterMessage) {
            ClusterMessage body = (ClusterMessage)msg.getObject();
            if(body.getCommand().equals("clearCache")) {
                logger.trace("Cluster Receiver Received Command : {}", new Object[]{body.getCommand()});
                // TODO clear cache job
            }
        }
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
                    JmxConfigurator.unregisterChannel((JChannel) channel, server, this.clusterName);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String nodeId = extractNodeIdFromView(channel.getView());
        if(this.clusterNodes.containsKey(nodeId)) {
            this.clusterNodes.get(nodeId).setState(EcmServiceState.FAIL);
        }
    }

    @Override
    public void channelClosed(Channel channel) {

    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        try {
            this.channel.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, ClusterInfo.ClusterNodeInfo> getClusterNodeInfos() {
        return this.clusterNodes;
    }
}
