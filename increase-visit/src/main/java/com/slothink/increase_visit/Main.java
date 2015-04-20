package com.slothink.increase_visit;

import com.comas.solme.framework.util.ResourceUtils;
import com.slothink.increase_visit.config.Config;
import com.slothink.increase_visit.config.ConfigReader;
import com.slothink.increase_visit.target.Naver;

import java.io.File;

/**
 * Created by slothink on 15. 3. 17..
 */
public class Main {

    public static void main(String[] args) {
        RuntimeInfo runtimeInfo = new RuntimeInfo();
        Config config = ConfigReader.parse(new File(runtimeInfo.getConfigDir(), "config.properties"));
        Naver naver = new Naver();
        Stressor stressor = new Stressor(naver, config, runtimeInfo);
        stressor.start();
        System.out.println("completed");
    }
}
