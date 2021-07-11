package com.sh.pojo.config.db;

public interface DaoFactory {

    static <T> T getInstance(Object obj) {
        return null;
    }

    static ConnectionMaker makeConnection() {
        return null;
    }
}
