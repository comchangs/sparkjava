package com.sh.pojo.config;

import com.sh.pojo.config.db.dbcp.ConnectionPool;
import com.sh.pojo.config.db.dbcp.DBConnectionInfo;
import com.sh.pojo.config.db.dbcp.DBConnectionPool;

public class AppConfigEx {
    private static final AppConfigEx APP_CONFIG = new AppConfigEx();

    private AppConfigEx() {
    }

    private static DBConnectionInfo dbConnectionInfo() {
        return DBConnectionInfo.builder()
                .driver("driver")
                .url("url")
                .id("id")
                .password("password")
                .build();
    }

    public static ConnectionPool makeConnectionPool(){
        return DBConnectionPool.create(dbConnectionInfo());
    }
}
