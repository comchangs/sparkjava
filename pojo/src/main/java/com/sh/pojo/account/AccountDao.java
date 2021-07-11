package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.jdbc.JdbcContext;
import com.sh.pojo.config.db.exception.DataAccessEsception;
import com.sh.pojo.config.db.jdbc.MapperRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class AccountDao implements AccountRepository {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public Boolean create() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE account (account_id int NOT NULL AUTO_INCREMENT PRIMARY KEY, nickname varchar(20) NOT NULL, password varchar(255) NOT NULL");
        query.append(", email varchar(50) NOT NULL, join_at datetime(6), password_update_date datetime(6), alarm_change_password datetime(6), receive_email bit(1) );");

        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query.toString(), null);
    }

    @Override
    public boolean save(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionMaker.makeConnection();
            String query = "INSERT INTO account (nickname, password, email, join_at, password_update_date, alarm_change_password, receive_email) VALUES (?,?,?,?,?,?,?);";

            statement = connection.prepareStatement(query);
            statement.setString(1,account.getNickname());
            statement.setString(2,account.getPassword());
            statement.setString(3,account.getEmail());
            statement.setObject(4,account.getJoinedAt());
            statement.setObject(5,account.getPasswordUpdateDate());
            statement.setBoolean(6,account.isAlarmChangePassword());
            statement.setBoolean(7,account.isReceiveEmail());
            int result = statement.executeUpdate();
            if(result!=1) return false;

        }  catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }
        return true;
    }

    @Override
    public Account findById(Long id) {
        String query = "SELECT * FROM account WHERE account_id = ? ";

        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<Account> mapperRow = resultSet -> new Account(
                resultSet.getLong("account_id"),
                resultSet.getString("nickname"),
                resultSet.getString("email"),
                resultSet.getObject("join_at", LocalDate.class),
                resultSet.getObject("password_update_date", LocalDate.class),
                resultSet.getBoolean("alarm_change_password"),
                resultSet.getBoolean("receive_email"));
        return jdbcContext.executeQueryInContext(query, mapperRow, id);
    }

    public List<AccountAdminResponse> findByAll(Page page){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM account a WHERE account_id  >= (SELECT COUNT(*) FROM account b)-? ");
        query.append(" AND a.account_id < (SELECT COUNT(*) FROM account c)-? ORDER BY a.account_id ASC;");

        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<AccountAdminResponse> mapperRow = resultSet -> {
            AccountAdminResponse account = new AccountAdminResponse();
            account.setId(resultSet.getLong("account_id"));
            account.setNickname(resultSet.getString("nickname"));
            account.setEmail(resultSet.getString("email"));
            account.setJoinedAt(resultSet.getObject("join_at", LocalDate.class));
            account.setPasswordUpdateDate(resultSet.getObject("password_update_date", LocalDate.class));
            account.setAlarmChangePassword(resultSet.getBoolean("alarm_change_password"));
            account.setReceiveEmail(resultSet.getBoolean("receive_email"));
            return account;
        };
        return jdbcContext.executeQueryListInContext(query.toString(), mapperRow, page.totalRows(), page.currentPage());
    }

    public Account findByNickname(String nickname) {
        String query = "select * from account where nickname=?";
        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<Account> mapperRow = resultSet -> new Account(
                resultSet.getLong("account_id"),
                resultSet.getString("nickname"),
                resultSet.getString("email"),
                resultSet.getObject("join_at", LocalDate.class),
                resultSet.getObject("password_update_date", LocalDate.class),
                resultSet.getBoolean("alarm_change_password"),
                resultSet.getBoolean("receive_email"));
        return jdbcContext.executeQueryInContext(query, mapperRow, nickname);
    }

    public boolean existsByEmail(String email){
        String query = "SELECT EXISTS (SELECT * FROM account WHERE email=?) as success;";
        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<Integer> mapperRow = resultSet -> resultSet.getInt("success");
        return jdbcContext.executeQueryInContext(query, mapperRow, email) == 1;
    }

    public boolean existsByNickname(String nickname){
        String query = "SELECT EXISTS (SELECT * FROM account WHERE nickname=?) as success;";
        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        MapperRow<Integer> mapperRow = resultSet -> resultSet.getInt("success");
        return jdbcContext.executeQueryInContext(query, mapperRow, nickname) == 1;
    }

    @Override
    public Boolean updatePassword(Account account) {
        String query = "UPDATE account SET password= ?, password_update_date= ? WHERE account_id=?";

        JdbcContext jdbcContext = new JdbcContext(connectionMaker);
        return jdbcContext.executeUpdateInContext(query, account.getPassword(), account.getPasswordUpdateDate(),account.getId());
    }

    @Override
    public Boolean update(Account account) {
        String query = "UPDATE account SET nickname= ?, email= ?, receive_email= ? WHERE account_id=?";
        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query, account.getNickname(), account.getEmail(), account.isReceiveEmail(), account.getId());
    }

    @Override
    public Boolean deleteById(Long id) {
        String query = "DELETE FROM account WHERE account_id=?";
        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query, id);
    }

    @Override
    public Boolean deleteAll() {
        String query = "truncate account";
        JdbcContext context = new JdbcContext(connectionMaker);
        return context.executeUpdateInContext(query);
    }

}
