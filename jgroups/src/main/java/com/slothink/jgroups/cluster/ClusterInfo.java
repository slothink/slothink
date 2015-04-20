package com.slothink.jgroups.cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slothink on 2015-04-20.
 */
public class ClusterInfo {
    private List<ClusterNodeInfo> nodes = new ArrayList<ClusterNodeInfo>();

    public List<ClusterNodeInfo> getNodes() {
        return nodes;
    }

    public void setNodes(List<ClusterNodeInfo> nodes) {
        this.nodes = nodes;
    }

    public static class ClusterNodeInfo {
        private String id;
        private String url;
        private EcmServiceState state;
        private boolean alive = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public EcmServiceState getState() {
            return state;
        }

        public void setState(EcmServiceState state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "ClusterNodeInfo{" +
                    "id='" + id + '\'' +
                    ", url='" + url + '\'' +
                    ", alive=" + alive +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClusterInfo{" +
                "nodes=" + nodes +
                '}';
    }
}
