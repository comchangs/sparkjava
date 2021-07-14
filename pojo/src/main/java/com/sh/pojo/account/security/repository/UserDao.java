package com.sh.pojo.account.security.repository;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.jdbc.JdbcContext;
import com.sh.pojo.config.db.jdbc.MapperRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserDao implements UserRepository {

    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    @Override
    public boolean save(User user) {
        String query = "INSERT INTO user (account_id, name, session_id, login_at, is_logined) VALUES (?,?,?,?,?);";

        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        return jdbcContext.executeUpdateInContext(query, user.getAccountId(), user.getName(), user.getSessionId() ,user.getLoginAt(), user.isLogined());
    }

    @Override
    public User findByAccountId(Long accountId) {
        String query = "SELECT * FROM user WHERE account_id=?";
        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<User> mapperRow = resultSet -> new User(
                resultSet.getLong("id"),
                resultSet.getLong("account_id"),
                resultSet.getString("name"),
                resultSet.getObject("login_at", LocalDateTime.class),
                resultSet.getObject("logout_at", LocalDateTime.class),
                resultSet.getBoolean("is_logined"));
        return jdbcContext.executeQueryInContext(query, mapperRow, accountId);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

      @Override
    public Boolean update(User obj) {
        return null;
    }

    @Override
    public Boolean deleteById(Long accountId) {
        String query = "DELETE FROM user WHERE account_id=?";
        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query, accountId);
    }

    @Override
    public Boolean deleteAll() {
        String query = "TRUNCATE user";
        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query);
    }

}
