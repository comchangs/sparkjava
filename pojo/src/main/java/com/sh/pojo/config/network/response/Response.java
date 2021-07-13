package com.sh.pojo.config.network.response;

import com.google.gson.JsonElement;
import com.sh.pojo.config.network.ResponseStatus;

import java.util.HashMap;

public class Response {

    private ResponseStatus status;

    private String message;

    private JsonElement data;

    public Response(ResponseStatus status) {
        this.status = status;
    }
    public Response(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public Response(ResponseStatus status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public static Response OK(){
        Response response = new Response(ResponseStatus.SUCCESS);
        response.setData(null);
        return response;
    }

    public static Response OK(JsonElement data) {
        return new Response(ResponseStatus.SUCCESS, data);
    }

    public static Response FAIL(JsonElement data) {
        return new Response(ResponseStatus.FAIL, data);
    }

    public static Response ERROR(String message) {
        return new Response(ResponseStatus.ERROR, message);
    }
}
