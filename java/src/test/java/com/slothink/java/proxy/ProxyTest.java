package com.slothink.java.proxy;

import com.slothink.java.proxy.LieProxyHandler;
import com.slothink.java.proxy.Man;
import com.slothink.java.proxy.RealMan;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by slothink on 2014-08-18.
 */
public class ProxyTest {
    @Test
    public void testProxy() {
        Man realMan = new RealMan(30);
        long start = System.currentTimeMillis();
        Man proxyMan = (Man) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Man.class}, new LieProxyHandler(realMan));
        long end = System.currentTimeMillis();
        System.out.println("proxy cost (unit:ms)="+(end-start));
        assertThat(realMan.getAge(), is(30));
        assertThat(proxyMan.getAge(), is(130));
    }
}
