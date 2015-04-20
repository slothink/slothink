package com.slothink.java.proxy;

/**
 * Created by slothink on 2014-08-18.
 */
public class RealMan implements Man{
    private int age = 0;

    public RealMan(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
