package com.slothink.java.file_lock;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by slothink on 2015-03-27.
 * - See more at: http://www.javabeat.net/locking-files-using-java/#sthash.zUbQHDMe.dpuf
 */
public class FileLockTest {
    public static void main(String[] args) throws Exception {

        RandomAccessFile file = null;
        FileLock fileLock = null;
        try {
            file = new RandomAccessFile("FileToBeLocked", "rw");
            FileChannel fileChannel = file.getChannel();

            fileLock = fileChannel.tryLock();
            if (fileLock != null) {
                System.out.println("File is locked");
                Thread.sleep(1000 * 10);
                accessTheLockedFile();
            }else {
                System.out.println("File lock is null");
            }
        } finally {
            if (fileLock != null) {
                fileLock.release();
            }
        }
    }

    static void accessTheLockedFile() {

        try {
            FileInputStream input = new FileInputStream("FileToBeLocked");
            int data = input.read();
            System.out.println(data);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
