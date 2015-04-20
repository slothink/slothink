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
            String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost 본인
            System.out.println("Connecting to server IP : " + serverIP);

            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIP, 5000);

            // 소켓의 입력스트림을 얻는다.
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            int value = ((int)(Math.random() * 1000000)) % 1000000;
            dos.writeInt(value);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);  // 기본형 단위로 처리하는 보조스트림

            // 소켓으로 부터 받은 데이터를 출력한다.
            System.out.println("Received server message : " + dis.readUTF());
            System.out.println("Close the connection.");

            // 스트림과 소켓을 닫는다.
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
