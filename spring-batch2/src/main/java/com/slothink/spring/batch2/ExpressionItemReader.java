package com.slothink.spring.batch2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

/**
 * Created by slothink on 2014-10-16.
 */
public class ExpressionItemReader implements ItemReader<Expression> {

    File watchDir = null;
    File doingDir = null;

    @PostConstruct
    public void init() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        watchDir = new File(tmpDir, "batch-test");
        if(!watchDir.exists()) {
            if(!watchDir.mkdir()) {
                throw new IllegalStateException("failed to create batch-test directory. target:"+watchDir.getAbsolutePath());
            }
        }
        doingDir = new File(tmpDir, "batch-doing");
        if(!doingDir.exists()) {
            if(!doingDir.mkdir()) {
                throw new IllegalStateException("failed to create batch-doing directory. target:"+doingDir.getAbsolutePath());
            }
        }
        System.out.println("reader dir:"+watchDir.getAbsolutePath());
        System.out.println("doing dir:"+doingDir.getAbsolutePath());
    }

    @Override
    public Expression read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        for(File file : watchDir.listFiles()) {
            if(file.isFile()) {
                BufferedReader reader = null;
                String line = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    line = reader.readLine();
                }finally {
                    IOUtils.closeQuietly(reader);
                }
                Expression expression = new Expression(line);
                FileUtils.moveFileToDirectory(file, doingDir, true);
                return expression;
            }
        }
        return null;
    }
}
