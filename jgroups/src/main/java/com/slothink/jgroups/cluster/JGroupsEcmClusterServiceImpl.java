package com.slothink.jgroups.cluster;

import org.jgroups.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by slothink on 2015-01-08.
 */
public class JGroupsEcmClusterServiceImpl implements JGroupsEcmClusterService  {

    private Logger logger = LoggerFactory.getLogger(JGroupsEcmClusterServiceImpl.class);

    private String clusterName = "slothink-cluster";
    private String clusterResourcePath="classpath:slothink.cluster.udp_default.xml";

    private JGroupsRunner runner = null;

    @Override
    public void connect() {
        if(runner != null) {

        }else {
            this.runner = new JGroupsRunner(clusterName, clusterResourcePath);
            new Thread(this.runner).start();
        }
    }

    @Override
    public void clearCache() {
        ClusterMessage messageBody = new ClusterMessage();
        messageBody.setCommand("clearCache");
        Message message = new Message(null, null, messageBody);
        try {
            this.runner.send(message);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    @Override
    public ClusterInfo getCachedClusterInfo() {
        ClusterInfo clusterInfo = new ClusterInfo();
        if(this.runner != null) {
            clusterInfo.getNodes().addAll(this.runner.getClusterNodeInfos().values());
        }
        return clusterInfo;
    }

    @Override
    public ClusterInfo getCachedActiveClusterInfo() {
        ClusterInfo clusterInfo = new ClusterInfo();
        if(this.runner != null) {
            for (ClusterInfo.ClusterNodeInfo nodeInfo : this.runner.getClusterNodeInfos().values()) {
                if (nodeInfo.getState() == EcmServiceState.READY) {
                    clusterInfo.getNodes().add(nodeInfo);
                }
            }
        }
        return clusterInfo;
    }
}
