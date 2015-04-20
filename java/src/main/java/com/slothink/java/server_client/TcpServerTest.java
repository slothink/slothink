package com.slothink.java.server_client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by slothink on 2015-03-26.
 * ref : http://gangzzang.tistory.com/85
 */
public class TcpServerTest {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // ���������� �����ϰ� 5000�� ��Ʈ�� ����(bind) ��Ų��.
            serverSocket = new ServerSocket(5000);
            System.out.println(getTime() + " Server ready");
        } catch (IOException e) {
            e.printStackTrace();
        } // try - catch

        while (true) {
            try {
                System.out.println(getTime() + " Waiting a connect request");
                // ���������� Ŭ���̾�Ʈ�� �����û�� �� ������ ������ ���߰� ��� ��ٸ���.
                // Ŭ���̾�Ʈ�� �����û�� ���� Ŭ���̾�Ʈ ���ϰ� ����� ���ο� ������ �����Ѵ�.
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                int value = dis.readInt();

                System.out.println(getTime() + " "+Thread.currentThread().getId()+"> Requested from :" + socket.getInetAddress()+" client:"+value);



                // ������ ��½�Ʈ���� ��´�.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out); // �⺻�� ������ ó���ϴ� ������Ʈ��

                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // ���� ����(remote socket)�� �����͸� ������.
                dos.writeUTF(Thread.currentThread().getId()+"> This is server message from client "+value);
                System.out.println(getTime() + " Send data");

                // ��Ʈ���� ������ �޾��ش�.
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // try - catch
        } // while
    } // main

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    } // getTime
} // TcpServerTest
