package com.sh.pojo.config.network.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static JsonElement jsend(String api , Object obj){
        Map<String, Object> map = new HashMap<>();
        map.put(api, obj);
        return new Gson().toJsonTree(map);
    }
}
