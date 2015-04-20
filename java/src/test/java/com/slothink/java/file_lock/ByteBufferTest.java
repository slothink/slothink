package com.slothink.java.file_lock;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by slothink on 2015-03-27.
 */
public class ByteBufferTest {

    /**
     * Same as the normal <tt>channel.read(b)</tt>, but tries to ensure
     * that the entire len number of bytes is read.
     * <p>
     * If the end of file is reached before any bytes are read, returns -1. If
     * the end of the file is reached after some bytes are read, returns the
     * number of bytes read. If the end of the file isn't reached before len
     * bytes have been read, will return len bytes.
     */
    public static int readFully(ReadableByteChannel channel, ByteBuffer b) throws IOException {
        int total = 0;
        while (true) {
            int got = channel.read(b);
            if (got < 0) {
                return (total == 0) ? -1 : total;
            }
            total += got;
            if (total == b.capacity() || b.position() == b.capacity()) {
                return total;
            }
        }
    }

    @Test
    public void testGetBytesFromByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[]{
                1, 2, 3, 4, 5, 6, 7, 8
        });
        byteBuffer.limit(5);
        byteBuffer.flip();

        byte[] first = new byte[byteBuffer.limit()];
        byteBuffer.get(first);
        assertThat(first.length, is(5));
        assertThat(first[0], is((byte) 1));
        assertThat(first[4], is((byte) 5));

        byteBuffer.limit(9);
        byte[] second = new byte[3];
        byteBuffer.get(second);
        assertThat(second.length, is(3));
        assertThat(second[0], is((byte) 6));
        assertThat(second[2], is((byte) 8));
    }

    @Test
    public void readEmptyFileByChannel() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("no-exist-file", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1000);
        assertThat(byteBuffer.position(), is(0));
        assertThat(byteBuffer.limit(), is(1000));
        assertThat(byteBuffer.capacity(), is(1000));

        int read = fileChannel.read(byteBuffer);
        assertThat(read, is(-1));
        assertThat(byteBuffer.position(), is(0));
        assertThat(byteBuffer.limit(), is(1000));
        assertThat(byteBuffer.capacity(), is(1000));

        int oneMoreRead = fileChannel.read(byteBuffer);
        assertThat(oneMoreRead, is(-1));

        fileChannel.close();
    }
}
