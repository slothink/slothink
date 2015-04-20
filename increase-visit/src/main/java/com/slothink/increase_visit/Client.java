package com.slothink.increase_visit;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slothink on 15. 3. 17..
 */
public class Client {

    private String url;
    private HeaderBuilder headerBuilder = new HeaderBuilder();
    private ParameterBuilder parameterBuilder = new ParameterBuilder();

    public Client(String url) {
        this.url = url;
    }

    public Client addHeader(String key, String value) {
        this.headerBuilder.add(key, value);
        return this;
    }

    public Client addParameter(String key, String value) {
        this.parameterBuilder.add(key, value);
        return this;
    }


    public String get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(parameterBuilder.buildTo(url));
        this.headerBuilder.buildTo(httpGet);

        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
// The underlying HTTP connection is still held by the response object
// to allow the response content to be streamed directly from the network socket.
// In order to ensure correct deallocation of system resources
// the user MUST call CloseableHttpResponse#close() from a finally clause.
// Please note that if response content is not fully consumed the underlying
// connection cannot be safely re-used and will be shut down and discarded
// by the connection manager.
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String content = null;
            try {
                content = EntityUtils.toString(entity1, "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                EntityUtils.consume(entity1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content;
        } finally {
            try {
                response1.close();
            } catch (IOException e) {
            }
        }
    }

    private void addHeader() {

    }

    public void post() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://targethost/login");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
    }
}
