package com.sh.pojo.config.db.dbcp;

public class DBConnectionInfoBuilder {
    private String driver;
    private String url;
    private String id;
    private String password;

     public DBConnectionInfoBuilder driver(String driver) {
        this.driver = driver;
        return this;
    }

    public DBConnectionInfoBuilder url(String url) {
        this.url = url;
        return this;
    }

    public DBConnectionInfoBuilder id(String id) {
        this.id = id;
        return this;
    }

    public DBConnectionInfoBuilder password(String password) {
        this.password = password;
        return this;
    }

    public DBConnectionInfo build() {
        DBConnectionInfo info = new DBConnectionInfo();
        info.setDriver(driver);
        info.setUrl(url);
        info.setId(id);
        info.setPassword(password);
        return info;
    }
}
