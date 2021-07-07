package com.sh.pojo.account.security.repository;

import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.config.db.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements UserRepository {

    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    @Override
    public boolean save(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            String sql = "INSERT INTO user (account_id, name, token, session_id, is_logined) VALUES (?,?,?,?,?);";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, user.getAccountId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getToken());
            statement.setString(4, user.getSessionId());
            statement.setBoolean(5, user.isLogined());

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
    public Object findById(Long id) {
        return null;
    }

    @Override
    public List<User> findByAll() {
        return null;
    }

    @Override
    public Boolean update(User obj) {
        return null;
    }

    @Override
    public Integer deleteById(Long id) {
        return null;
    }
}
