package com.slothink.increase_visit.worker;

import com.slothink.increase_visit.config.Config;
import com.vine.util.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by slothink on 15. 3. 17..
 */
public class WorkerReader {

    public static Worker parse(InputStream is) {
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

    public static Worker parse(File file)  {
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

    public static Worker parse(Properties properties) {
        Worker worker = new Worker();
        String userAgent = properties.getProperty("User-Agent");
        worker.setUserAgent(userAgent);
        return worker;
    }

}
