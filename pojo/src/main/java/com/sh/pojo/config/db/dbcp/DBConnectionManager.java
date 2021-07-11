package com.sh.pojo.config.db.dbcp;

import com.sh.pojo.config.db.ConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionManager implements ConnectionMaker {

    ConnectionPool pool;

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        return null;
    }

    @Override
    public void returnConnection(Connection conn) {

    }

    @Override
    public void close() {

    }
}
