package com.sh.pojo.config.network.header;

import java.util.HashMap;
import java.util.Map;

public class Data {

    private Map<String, Object> jSendData = new HashMap<>();

    public void put(String key, Object value){
        jSendData.put(key, value);
    }

    public Map<String, Object> getData(){
        return jSendData;
    }


}
