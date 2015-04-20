package com.slothink.spring.batch2;

import org.springframework.batch.item.ItemWriter;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

/**
 * Created by slothink on 2014-10-16.
 */
public class ExpressionItemWriter implements ItemWriter<Expression> {

    File toDir = null;

    @PostConstruct
    public void init() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        toDir = new File(tmpDir, "batch-test-to");
        if(!toDir.exists()) {
            if(!toDir.mkdir()) {
                throw new IllegalStateException("failed to create batch-test directory. target:"+ toDir.getAbsolutePath());
            }
        }
    }
    @Override
    public void write(List<? extends Expression> expressions) throws Exception {
        for(Expression expression : expressions) {
            File to = new File(toDir, UUID.randomUUID().toString());
            BufferedWriter writer = new BufferedWriter(new FileWriter(to));
            writer.write(expression.getExpression() + "=" + String.valueOf(expression.getResult()));
            writer.close();
        }
    }
}
