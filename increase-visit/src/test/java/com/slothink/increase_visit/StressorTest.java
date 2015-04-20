package com.slothink.increase_visit;

import com.comas.solme.framework.util.ResourceUtils;
import com.slothink.increase_visit.config.Config;
import com.slothink.increase_visit.target.Naver;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by slothink on 15. 3. 17..
 */
public class StressorTest {

    @Test
    @Ignore
    public void test() {

        File baseDir = null;
        try {
            baseDir =
                    new File(
                        ResourceUtils.getResource("classpath:com/slothink/increase_visit/StressorTest.class").getFile()
                        .getParentFile().getParentFile().getParentFile().getParentFile() // target/test-classes
                        .getParentFile().getParentFile() // ${base_dir}
                       , "src/test/resources"
                    );
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(baseDir.getAbsolutePath());

        RuntimeInfo runtimeInfo = new RuntimeInfo(baseDir);
        Config config = new Config();

        Naver naver = new Naver();

        Stressor stressor = new Stressor(naver, config, runtimeInfo);
        stressor.start();

    }

    @Test
    @Ignore
    public void testNaver() {

        File baseDir = null;
        try {
            baseDir =
                    new File(
                            ResourceUtils.getResource("classpath:com/slothink/increase_visit/StressorTest.class").getFile()
                                    .getParentFile().getParentFile().getParentFile().getParentFile() // target/test-classes
                                    .getParentFile().getParentFile() // ${base_dir}
                            , "src/test/resources"
                    );
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(baseDir.getAbsolutePath());

        RuntimeInfo runtimeInfo = new RuntimeInfo(baseDir);
        Config config = new Config();
        config.setRepeat(-1);
        config.setWait(0);

        Naver naver = new Naver();

        Stressor stressor = new Stressor(naver, config, runtimeInfo);
        stressor.start();

    }

}
