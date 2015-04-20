package com.slothink.find_disk;

import com.comas.solme.framework.util.ResourceUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.size;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 26
 * Time: 오후 5:41
 * To change this template use File | Settings | File Templates.
 */
public class FindDiskTest {

    @Test
    public void test() {
        assertThat(findDisk("sdbs").get(0).getName(), is("id010a_sr_55"));
        assertThat(findDisk("sdbs").size(), is(1));
        assertThat(findDisk("sdij").get(0).getName(), is("id010a_sr_40"));
        assertThat(findDisk("sdij").size(), is(1));
        assertThat(findDisk("sdcj").get(0).getName(), is("id010a_sr_72"));
        assertThat(findDisk("sdcj").size(), is(1));
        assertThat(findDisk("sdsq").get(0).getName(), is("id010b_om_52"));
        assertThat(findDisk("sdsq").size(), is(1));
        assertThat(findDisk("sdm").get(0).getName(), is("id010b_local_6"));
        assertThat(findDisk("sdm").size(), is(1));

    }

    private List<Volumn> findDisk(String diskName) {
        File logFile = null;
        try {
            logFile = ResourceUtils.getFile("classpath:sample.log");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ParseRequest request = new ParseRequest(logFile.getAbsolutePath(), diskName);
        CommandParser parser = new CommandParser();
        try {
            return parser.findVolune(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


