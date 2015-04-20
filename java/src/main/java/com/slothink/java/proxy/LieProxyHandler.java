package com.slothink.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by slothink on 2014-08-18.
 */
public class LieProxyHandler implements InvocationHandler{
    private Man target;
    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass()
                        .getClassLoader(), obj.getClass().getInterfaces(),
                new LieProxyHandler((Man)obj));
    }

    public LieProxyHandler(Man target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        return ((Integer)result+100);
    }
}
