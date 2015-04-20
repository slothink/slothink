package com.slothink.java.concurrent;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by slothink on 2015-03-26.
 */
public class ConcurrentFileAccessTest {

    File getRandomFile() {
        File file = new File(new File((String) System.getProperties().get("java.io.tmpdir")), UUID.randomUUID().toString());
        return file;
    }

    @Test
    public void testWriteOpenAndReadTest() throws IOException {
        File file = this.getRandomFile();
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(file, "rw");
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
        randomAccessFile1.write(1);
        assertThat(randomAccessFile2.read(), is(1));
        randomAccessFile2.seek(0);
        randomAccessFile2.write(2);
        randomAccessFile2.write(3);
        assertThat(randomAccessFile1.read(), is(3));
        randomAccessFile1.seek(0);
        assertThat(randomAccessFile1.read(), is(2));
        randomAccessFile1.close();
        randomAccessFile2.close();
    }
}
