package com.slothink.increase_visit;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slothink on 15. 3. 17..
 */
public class HeaderBuilder {
    private List<Header> headers = new ArrayList<Header>();

    public HeaderBuilder add(String key, String value) {
        this.headers.add(new BasicHeader(key, value));
        return this;
    }

    public void buildTo(HttpGet get) {
        for(Header header : this.headers) {
            get.addHeader(header);
        }
    }
}
