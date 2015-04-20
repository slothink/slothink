package com.slothink.increase_visit;

import com.comas.solme.framework.util.ResourceUtils;
import com.comas.solme.framework.util.resource.Resource;
import com.vine.util.io.FileUtils;
import com.vine.util.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by slothink on 15. 3. 17..
 */
public class RuntimeInfo {
    private File baseDir;
    private File reportDir;
    private File configDir;
    private File workerDir;

    public RuntimeInfo(File baseDir) {
        this.baseDir = baseDir;
        this.init();
    }

    public RuntimeInfo() {
        try {
            Resource resource = ResourceUtils.getResource("classpath:com/slothink/increase_visit/Main.class");
            if(ResourceUtils.isJarURL(resource.getURL())) {
                this.baseDir = ResourceUtils.getFile(ResourceUtils.extractJarFileURL(resource.getURI().toURL())).getParentFile().getParentFile();
            }else {
                this.baseDir = ResourceUtils.getResource("classpath:com/slothink/increase_visit/Main.class").getFile()
                        .getParentFile().getParentFile().getParentFile().getParentFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.init();
    }

    private void init() {
        this.configDir = new File(baseDir, "config");
        this.reportDir = new File(baseDir, "report");
        this.workerDir = new File(baseDir, "worker");
        try {
            FileUtils.forceMkdir(reportDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getBaseDir() {
        return baseDir;
    }

    public File getReportDir() {
        return reportDir;
    }

    public File getConfigDir() {
        return configDir;
    }

    public File getWorkerDir() {
        return workerDir;
    }
}
