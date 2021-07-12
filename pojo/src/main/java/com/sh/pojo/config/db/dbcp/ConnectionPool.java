package com.sh.pojo.config.db.dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;

public interface ConnectionPool {

    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    Queue<Connection> getConnectionPool();

    int getSize();

    String getUrl();

    String getUser();

    String getPassword();

    void shutdown() throws SQLException;;

}
