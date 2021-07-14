package com.sh.pojo.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestResponse {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String status;
//    private String body;

    public TestResponse(String response) {
        Map<String,String> fromJson = new Gson().fromJson(response, HashMap.class);
        this.status = fromJson.get("status");
    }

    public String getStatus() {
        return status;
    }

}
