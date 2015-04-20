package com.slothink.jgroups.cluster;

import java.io.Serializable;

/**
 * Created by slothink on 2015-01-20.
 */
public class ClusterMessage  implements Serializable{

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClusterMessage that = (ClusterMessage) o;

        if (command != null ? !command.equals(that.command) : that.command != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return command != null ? command.hashCode() : 0;
    }
}
