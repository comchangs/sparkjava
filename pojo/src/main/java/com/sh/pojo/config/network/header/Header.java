package com.sh.pojo.config.network.header;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Header {
    // api 응답코드
    private String status;

    // error message
    private String message;

    // data
    private HashMap<String, Object> data = new HashMap<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String api, Object data) {
        if (Objects.isNull(api) && Objects.isNull(data)) this.data = null;
        else this.data.put(api, data);
    }

    //  OK
    public static Header OK() {
        return (Header) Header.builder()
                .status("success")
                .data(null,null)
                .build();
    }

    //  DATA OK
    public static Header OK(String api, Object data) {
        return (Header) Header.builder()
                .status("success")
                .data(api, data)
                .build();
    }

    // FAIL
    public static Header fail(String resons, Object data) {
        return (Header) Header.builder()
                .status("fail")
                .data(resons, data)
                .build();
    }
    // ERROR
    public static Header error() {
        return (Header) Header.builder()
                .status("error")
                .message("Unable to communicate with database")
                .build();
    }

    public static HeaderBuilder builder() {
        return new HeaderBuilder();
    }
}
