package com.slothink.avro_client_test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * Created by slothink on 14. 2. 5.
 */
public class ByteBufferTest {
    @Test
    public void testCopy() throws IOException {
        Date start = new Date();
        File sourceFile = new File("d:/source.pdf");
//		FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream("d:/target.copy0.pdf");
        byte[] bytes = new byte[(int) sourceFile.length()];

//		fis.read(buffer);
//		FileChannel fci = fis.getChannel();
        FileChannel fco = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        fco.write(buffer);
        buffer.clear();
        fco.close();
        Date end = new Date();
        System.out.println("copy file 1. cost time="+(end.getTime() - start.getTime()));
    }

    @Test
    public void copyFile1() throws IOException {
        Date start = new Date();
        FileInputStream fis = new FileInputStream("d:/source.pdf");
        FileOutputStream fos = new FileOutputStream("d:/target.pdf");

        FileChannel fci = fis.getChannel();
        FileChannel fco = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while(true) {
            int read = fci.read(buffer);
            if(read == -1) {
                break;
            }

            buffer.flip();

            fco.write(buffer);
            buffer.clear();
        }
        fci.close();
        fco.close();
        Date end = new Date();
        System.out.println("copy file 1. cost time="+(end.getTime() - start.getTime()));
    }

    @Test
    public void copyFile2() throws IOException {
        Date start = new Date();
        FileInputStream fis = new FileInputStream("d:/source.pdf");
        FileOutputStream fos = new FileOutputStream("d:/target2.pdf");

        byte[] buffer = new byte[1024];
        while(true) {
            int read = fis.read(buffer);
            if(read == -1) {
                break;
            }
            fos.write(buffer, 0, read);
        }
        fis.close();
        fos.close();
        Date end = new Date();
        System.out.println("copy file 2. cost time="+(end.getTime() - start.getTime()));
    }

    @Test
    public void testManyCopy1() throws IOException {
        Date start = new Date();
        for(int i = 0; i < 100; i++) {
            this.copyFile1();
        }
        Date end = new Date();
        System.out.println("many copy file 1. cost time="+(end.getTime() - start.getTime()));
    }

    @Test
    public void testManyCopy2() throws IOException {
        Date start = new Date();
        for(int i = 0; i < 100; i++) {
            this.copyFile2();
        }
        Date end = new Date();
        System.out.println("many copy file 2. cost time="+(end.getTime() - start.getTime()));
    }
}
