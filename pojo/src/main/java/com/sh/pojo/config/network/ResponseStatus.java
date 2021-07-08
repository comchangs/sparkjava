package com.sh.pojo.config.network;

public enum ResponseStatus {

    SUCCESS("success"),
    FAIL("fail"),
    ERROR("error");

    private String status;

    ResponseStatus(String sattus) {
    }

//    public String getStatus() {
//        return status;
//    }
}
