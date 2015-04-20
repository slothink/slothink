package com.slothink.increase_visit;

import com.slothink.increase_visit.config.Config;
import com.slothink.increase_visit.target.Daum;
import com.slothink.increase_visit.target.Naver;
import com.slothink.increase_visit.target.Target;
import com.slothink.increase_visit.worker.Worker;
import com.slothink.increase_visit.worker.WorkerReader;
import com.vine.util.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by slothink on 15. 3. 17..
 */
public class Stressor {

    private Config config;
    private RuntimeInfo runtimeInfo;
    private Target target;

    public Stressor(Target target, Config config, RuntimeInfo runtimeInfo) {
        this.target = target;
        this.config = config;
        this.runtimeInfo = runtimeInfo;
    }

    private List<String> getKeywords() {
        File keywordFile = new File(runtimeInfo.getConfigDir(), "keyword.txt");
        List<String> keywords = new ArrayList<String>();
        try {
            keywords.addAll(FileUtils.readLines(keywordFile, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return keywords;
    }

    private List<Worker> getWorkers() {
        Collection<File> workerFiles = FileUtils.listFiles(runtimeInfo.getWorkerDir(), new String[]{"properties"}, false);
        List<Worker> workers = new ArrayList<Worker>();
        for(File workerFile : workerFiles) {
            Worker worker = WorkerReader.parse(workerFile);
            workers.add(worker);
        }
        return workers;
    }

    public void start() {
        int count = 0;
        while(config.getRepeat() < 0 || count < config.getRepeat()) {
            String keyword = this.getRandomKeyword();
            String content = target.increase(this.getRandomWorker().getUserAgent(),
                    keyword);
            try {
                FileUtils.writeStringToFile(new File(runtimeInfo.getReportDir(), target.getName()+"-"+count + ".html"), content, "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println(count+": " +keyword);
            if(config.getWait() != 0) {
                try {
                    Thread.sleep(config.getWait());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getRandomKeyword() {
        List<String> keywords = this.getKeywords();
        int index = ((int) (Math.random() * 100000000)) % keywords.size();
        return keywords.get(index);
    }

    public Worker getRandomWorker() {
        List<Worker> workers = this.getWorkers();
        int index = ((int) (Math.random() * 100000000)) % workers.size();
        return workers.get(index);
    }

}
