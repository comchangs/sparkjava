package com.sh.pojo.config.network;

import com.sh.pojo.config.network.header.Header;

public class ResponseBody {

//    private static final ResponseBody RESPONSE_BODY = new ResponseBody();

    private ResponseBody() {

    }

    public static Header of(boolean result, String api, Object body, String message) {
        if (result) {
            if (body == null) {
                return Header.OK();
            }
            return Header.OK(api, body);
        } else {
            if (body != null) {
                return Header.fail(api, body);
            }
            return Header.error();
        }
    }
}
