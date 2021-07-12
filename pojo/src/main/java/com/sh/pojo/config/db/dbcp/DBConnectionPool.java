package com.sh.pojo.config.db.dbcp;

import com.sh.pojo.config.db.exception.DbConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DBConnectionPool implements ConnectionPool{

    private final String url;
    private final String user;
    private final String password;
    private final  LinkedBlockingQueue<Connection> connectionPool;

    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int MAX_TIMEOUT = 5;

    public static DBConnectionPool create(DBConnectionInfo dbConnectionInfo) {
        LinkedBlockingQueue<Connection> pool = new LinkedBlockingQueue<Connection>(MAX_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(makeConnection(dbConnectionInfo.getUrl(), dbConnectionInfo.getId(), dbConnectionInfo.getPassword()));
            } catch (SQLException throwables) {
                throw new DbConnectionException(throwables);
            }
        }
        return new DBConnectionPool(dbConnectionInfo.getUrl(), dbConnectionInfo.getId(), dbConnectionInfo.getPassword(), pool);
    }

    public DBConnectionPool(String url, String user, String password, LinkedBlockingQueue<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    private static Connection makeConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(makeConnection(url, user, password));
            } else {
                throw new RuntimeException("Because of maximum pool size reached, no available connection");
            }
        }

        Connection connection = connectionPool.poll();

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = makeConnection(url, user, password);
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public Queue<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void shutdown(){
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException throwables) {
                throw new DbConnectionException(throwables);
            }
        }
        connectionPool.clear();
    }
}
