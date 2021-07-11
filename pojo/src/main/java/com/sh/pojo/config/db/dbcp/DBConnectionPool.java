package com.sh.pojo.config.db.dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;

public class DBConnectionPool implements ConnectionPool{
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        return false;
    }

    @Override
    public Queue<Connection> getConnectionPool() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void shutdown() throws SQLException {

    }
}
