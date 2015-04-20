package com.slothink.spring.batch2;

import com.comas.solme.framework.util.LogUtil;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by slothink on 2014-10-17.
 */
public class JobExplorerMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/batch.xml");
        JobExplorer explorer = (JobExplorer)applicationContext.getBean("jobExplorer");
        List<JobInstance> jobInstanceList = explorer.getJobInstances("endOfDay", 0, 100);
        for(JobInstance jobInstance : jobInstanceList) {
            List<JobExecution> executions = explorer.getJobExecutions(jobInstance);
            for(JobExecution execution : executions) {
                for(Throwable e : execution.getFailureExceptions()) {
                    System.out.println(LogUtil.getStrackTrace(e));
                }
                System.out.println(execution);
            }
        }
    }
}
