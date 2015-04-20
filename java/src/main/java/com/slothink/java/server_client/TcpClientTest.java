package com.slothink.java.server_client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by slothink on 2015-03-26.
 * ref : http://gangzzang.tistory.com/85
 */
public class TcpClientTest {
    public static void main(String[] args) {
        try {
            String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost ����
            System.out.println("Connecting to server IP : " + serverIP);

            // ������ �����Ͽ� ������ ��û�Ѵ�.
            Socket socket = new Socket(serverIP, 5000);

            // ������ �Է½�Ʈ���� ��´�.
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            int value = ((int)(Math.random() * 1000000)) % 1000000;
            dos.writeInt(value);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);  // �⺻�� ������ ó���ϴ� ������Ʈ��

            // �������� ���� ���� �����͸� ����Ѵ�.
            System.out.println("Received server message : " + dis.readUTF());
            System.out.println("Close the connection.");

            // ��Ʈ���� ������ �ݴ´�.
            dis.close();
            socket.close();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } // try - catch
    } // main
} // TcpClientTest
