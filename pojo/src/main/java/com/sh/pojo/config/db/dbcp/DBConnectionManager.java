package com.sh.pojo.config.db.dbcp;

import com.sh.pojo.config.AppConfig;
import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.exception.DbConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionManager implements ConnectionMaker {

    private static final DBConnectionManager DB_CONNECTION_MANAGER = new DBConnectionManager(AppConfig.makeConnectionPool());

    private ConnectionPool pool;

    private DBConnectionManager(ConnectionPool connectionPool) {
        pool = connectionPool;
    }

    public static DBConnectionManager getInstance(){
        return DB_CONNECTION_MANAGER;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        return pool.getConnection();
    }

    @Override
    public boolean returnConnection(Connection connection) {
        return pool.releaseConnection(connection);
    }

    @Override
    public void close() {
        try {
            pool.shutdown();
        } catch (SQLException throwables) {
            throw new DbConnectionException(throwables);
        }
    }
}
