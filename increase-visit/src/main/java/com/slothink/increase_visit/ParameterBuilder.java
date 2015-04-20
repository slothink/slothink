package com.slothink.increase_visit;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slothink on 15. 3. 17..
 */
public class ParameterBuilder {
    private List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    public ParameterBuilder add(String key, String value) {
        this.parameters.add(new BasicNameValuePair(key, value));
        return this;
    }

    public String buildTo(String url) {
        if(url.indexOf("?") < 0) {
            url += "?";
        }
        String encodeUrl = URLEncodedUtils.format(this.parameters, "UTF-8");
        return url + encodeUrl;
    }
}
