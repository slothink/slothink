package com.slothink.jgroups;

/**
 * Created by slothink on 2014-12-29.
 */
public class Option {
    private String props;
    private boolean no_channel = false;
    private boolean jmx = false;
    private boolean use_state = false;
    private long state_timeout = 5000;
    private boolean use_unicasts = false;
    private String name;
    private boolean send_own_state_on_merge = true;

    public Option() {

    }

    public Option(String props, boolean no_channel, boolean jmx, boolean use_state, long state_timeout, boolean use_unicasts, String name, boolean send_own_state_on_merge) {
        this.props = props;
        this.no_channel = no_channel;
        this.jmx = jmx;
        this.use_state = use_state;
        this.state_timeout = state_timeout;
        this.use_unicasts = use_unicasts;
        this.name = name;
        this.send_own_state_on_merge = send_own_state_on_merge;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public boolean isNo_channel() {
        return no_channel;
    }

    public void setNo_channel(boolean no_channel) {
        this.no_channel = no_channel;
    }

    public boolean isJmx() {
        return jmx;
    }

    public void setJmx(boolean jmx) {
        this.jmx = jmx;
    }

    public boolean isUse_state() {
        return use_state;
    }

    public void setUse_state(boolean use_state) {
        this.use_state = use_state;
    }

    public long getState_timeout() {
        return state_timeout;
    }

    public void setState_timeout(long state_timeout) {
        this.state_timeout = state_timeout;
    }

    public boolean isUse_unicasts() {
        return use_unicasts;
    }

    public void setUse_unicasts(boolean use_unicasts) {
        this.use_unicasts = use_unicasts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSend_own_state_on_merge() {
        return send_own_state_on_merge;
    }

    public void setSend_own_state_on_merge(boolean send_own_state_on_merge) {
        this.send_own_state_on_merge = send_own_state_on_merge;
    }

    @Override
    public String toString() {
        return "DrawOption{" +
                "send_own_state_on_merge=" + send_own_state_on_merge +
                ", name='" + name + '\'' +
                ", props='" + props + '\'' +
                ", no_channel=" + no_channel +
                ", jmx=" + jmx +
                ", use_state=" + use_state +
                ", state_timeout=" + state_timeout +
                ", use_unicasts=" + use_unicasts +
                '}';
    }
}
