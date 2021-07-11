package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.exception.DataAccessEsception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AccountDao implements AccountRepository {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void create() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE account (account_id int NOT NULL AUTO_INCREMENT PRIMARY KEY, nickname varchar(20) NOT NULL, password varchar(255) NOT NULL");
            query.append(", email varchar(50) NOT NULL, join_at datetime(6), password_update_date datetime(6), alarm_change_password datetime(6), receive_email bit(1) );");

            statement = connection.createStatement();
            int result = statement.executeUpdate(query.toString());

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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account getAccount = null;
        try {
            connection = connectionMaker.makeConnection();
            String query = "SELECT * FROM account WHERE account_id = ? ";
            statement = connection.prepareStatement(query);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();

            if(!resultSet.next()) return getAccount;

            getAccount = new Account(
                    resultSet.getLong("account_id"),
                    resultSet.getString("nickname"),
                    resultSet.getString("email"),
                    resultSet.getObject("join_at", LocalDate.class),
                    resultSet.getObject("password_update_date",LocalDate.class),
                    resultSet.getBoolean("alarm_change_password"),
                    resultSet.getBoolean("receive_email")
            );
        }  catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if( resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }

        return getAccount;
    }

    public List<AccountAdminResponse> findByAll(Page page){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AccountAdminResponse> accountList = new CopyOnWriteArrayList<>();
        try {
            connection = connectionMaker.makeConnection();

            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM account a WHERE account_id  > (SELECT COUNT(*) FROM account b)-? ");
            query.append(" AND a.account_id <= (SELECT COUNT(*) FROM account c)-? ORDER BY a.account_id ASC;");
            statement = connection.prepareStatement(query.toString());
            statement.setInt(1, page.totalRows());     // page 10개 기준
            statement.setInt(2, page.currentPage());

            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                AccountAdminResponse account = new AccountAdminResponse();
                account.setId(resultSet.getLong("account_id"));
                account.setNickname(resultSet.getString("nickname"));
                account.setEmail(resultSet.getString("email"));
                account.setJoinedAt(resultSet.getObject("join_at", LocalDate.class));
                account.setPasswordUpdateDate(resultSet.getObject("password_update_date", LocalDate.class));
                account.setAlarmChangePassword(resultSet.getBoolean("alarm_change_password"));
                account.setReceiveEmail(resultSet.getBoolean("receive_email"));

                accountList.add(account);
            }

        }  catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if( resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }
        return accountList;
    }

    public Account findByNickname(String nickname) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account getAccount = null;

        try {
            connection = connectionMaker.makeConnection();

            String sql = "select * from account where nickname=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nickname);
            resultSet = statement.executeQuery();

            if(!resultSet.next()) return null;

            getAccount = new Account(
                    resultSet.getLong("account_id"),
                    resultSet.getString("nickname"),
                    resultSet.getString("email"),
                    resultSet.getObject("join_at", LocalDate.class),
                    resultSet.getObject("password_update_date",LocalDate.class),
                    resultSet.getBoolean("alarm_change_password"),
                    resultSet.getBoolean("receive_email")
            );

        }  catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if( resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }
        return getAccount;
    }


    public boolean existsByEmail(SignUpForm form){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = connectionMaker.makeConnection();
            String query = "SELECT EXISTS (SELECT * FROM account WHERE email=?) as success;";
            statement = connection.prepareStatement(query);
            statement.setString(1, form.getEmail());
            resultSet = statement.executeQuery();

            if(!resultSet.next()) return result;
            if(resultSet.getInt("success")==1) result = true;

        }  catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if( resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }
        return result;
    }

    public boolean existsByNickname(SignUpForm form){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = connectionMaker.makeConnection();
            String query = "SELECT EXISTS (SELECT * FROM account WHERE nickname=?) as success;";
            statement = connection.prepareStatement(query);
            statement.setString(1, form.getNickname());
            resultSet = statement.executeQuery();

            if(!resultSet.next()) return result;
            if(resultSet.getInt("success")==1) result = true;

        } catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessEsception(e);
        } finally {
            try {
                if( resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if ( connection != null ) connection.close();
            } catch (SQLException e){
                throw new DataAccessEsception(e);
            }
        }
        return result;
    }

    @Override
    public Boolean updatePassword(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            String query = "UPDATE account SET password= ?, password_update_date= ? WHERE account_id=?";
            statement = connection.prepareStatement(query);
            statement.setString(1,account.getPassword());
            statement.setObject(2,account.getPasswordUpdateDate());
            statement.setLong(3, account.getId());

            int result = statement.executeUpdate();
            if(result!=1) return false;

        } catch (ClassNotFoundException | SQLException e) {
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
    public Boolean update(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            String query = "UPDATE account SET nickname= ?, email= ?, receive_email= ? WHERE account_id=?";
            statement = connection.prepareStatement(query);
            statement.setString(1,account.getNickname());
            statement.setObject(2,account.getEmail());
            statement.setBoolean(3,account.isReceiveEmail());
            statement.setLong(4, account.getId());

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
    public Integer deleteById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = connectionMaker.makeConnection();
            String sql = "DELETE FROM account WHERE account_id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            result = statement.executeUpdate();
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
        return result;
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionMaker.makeConnection();
            String sql = "truncate account;";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
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
    }


}
