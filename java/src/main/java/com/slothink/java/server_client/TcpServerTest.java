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
            // 서버소켓을 생성하고 5000번 포트와 결합(bind) 시킨다.
            serverSocket = new ServerSocket(5000);
            System.out.println(getTime() + " Server ready");
        } catch (IOException e) {
            e.printStackTrace();
        } // try - catch

        while (true) {
            try {
                System.out.println(getTime() + " Waiting a connect request");
                // 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
                // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                int value = dis.readInt();

                System.out.println(getTime() + " "+Thread.currentThread().getId()+"> Requested from :" + socket.getInetAddress()+" client:"+value);



                // 소켓의 출력스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림

                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // 원격 소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF(Thread.currentThread().getId()+"> This is server message from client "+value);
                System.out.println(getTime() + " Send data");

                // 스트림과 소켓을 달아준다.
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
