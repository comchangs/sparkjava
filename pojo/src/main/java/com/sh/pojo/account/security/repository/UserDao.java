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

        String query = "INSERT INTO user (account_id, name, login_at, is_logined) VALUES (?,?,?,?);";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, user.getAccountId());
            statement.setString(2, user.getName());
            statement.setObject(3, user.getLoginAt());
            statement.setBoolean(4, user.isLogined());

            int result = statement.executeUpdate();
            if(result != 1) return false;
            
        }catch (ClassNotFoundException | SQLException e1) {
            System.out.println("시큐리티 user 저장 에러");
        } finally {
            if (statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
        return true;
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
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteAll() {
        return null;

    }

    @Override
    public User findByAccountId(Long accountId) {
        String query = "SELECT * FROM User WHERE account_id=?";
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
}
