package com.slothink.jgroups;

import org.jgroups.JChannel;

/**
 * Created by slothink on 2014-11-18.
 */
public class Draw1 extends org.jgroups.demos.Draw{

    public Draw1(String props, boolean no_channel, boolean jmx, boolean use_state, long state_timeout, boolean use_unicasts, String name, boolean send_own_state_on_merge) throws Exception {
        super(props, no_channel, jmx, use_state, state_timeout, use_unicasts, name, send_own_state_on_merge);
    }

    public Draw1(JChannel channel) throws Exception {
        super(channel);
    }

    public Draw1(JChannel channel, boolean use_state, long state_timeout) throws Exception {
        super(channel, use_state, state_timeout);
    }
}
