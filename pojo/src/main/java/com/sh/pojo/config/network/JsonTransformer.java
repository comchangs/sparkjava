package com.sh.pojo.config.network;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        System.out.println("render > "+model.toString());
        return gson.toJson(model);
    }
}
