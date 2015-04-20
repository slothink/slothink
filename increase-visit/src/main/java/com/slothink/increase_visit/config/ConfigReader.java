package com.slothink.increase_visit.config;

import com.vine.util.io.IOUtils;
import com.vine.util.lang.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by slothink on 15. 3. 17..
 */
public class ConfigReader {

    public static Config parse(InputStream is) {
        if(is == null) {
            throw new IllegalArgumentException("input stream is null");
        }
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return parse(properties);
    }

    public static Config parse(File file)  {
        if(file == null) {
            throw new IllegalArgumentException("config file parameter is null");
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parse(properties);
    }

    public static Config parse(Properties properties) {
        Config config = new Config();
        String wait = properties.getProperty("wait");
        if(wait != null) {
            config.setWait(Integer.valueOf(wait));
        }
        String repeat = properties.getProperty("repeat");
        if(repeat != null) {
            config.setRepeat(Integer.valueOf(repeat));
        }
        return config;
    }

}
