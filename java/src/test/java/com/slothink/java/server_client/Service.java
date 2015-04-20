package com.slothink.java.server_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by slothink on 2015-03-26.
 */
public class Service extends Thread {
    private Socket socket = null;
    private OutputStream out = null;
    private InputStream in = null;

    public Service(Socket socket) {
        this.socket = socket;
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run()
    {
        while(true) {
            int request = -1;
            try {
                request = in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String response = processReq(request);
            try {
                out.write(response.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String processReq(int request) {
        return "response:"+request;
    }
}
