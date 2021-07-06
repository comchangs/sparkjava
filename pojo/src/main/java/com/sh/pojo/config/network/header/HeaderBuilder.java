package com.sh.pojo.config.network.header;

public class HeaderBuilder {

    private String status;
    private String message;
    private String api;
    private Object data;

    public HeaderBuilder() {
    }

    public HeaderBuilder status(String status) {
        this.status = status;
        return this;
    }

    public HeaderBuilder message(String message) {
        this.message = message;
        return this;
    }

    public HeaderBuilder data(String api, Object data) {
        this.api = api;
        this.data = data;
        return this;
    }

    public Header build(){
        Header header = new Header();
        header.setStatus(status);
        header.setData(api, data);
        header.setMessage(message);
        return  header;
    }


}
