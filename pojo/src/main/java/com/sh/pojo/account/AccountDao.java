package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.db.ConnectionMaker;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AccountDao implements AccountRepository {

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
            query.append("CREATE TABLE account (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, nickname varchar(20) NOT NULL, password varchar(255) NOT NULL");
            query.append(", email varchar(50) NOT NULL, join_at datetime(6), password_update_date datetime(6), alarm_change_password datetime(6), receive_email bit(1) );");

            statement = connection.createStatement();

            int result = statement.executeUpdate(query.toString());

        }  catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            System.out.println("error of account's create query");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // here not throw out, complete for connection close
                }
            }
            if ( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }


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

        }  catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            System.out.println("account save 에러");
        } finally {
            if (statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
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
    public Account findById(Long id) {
        System.out.println("db id > "+id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account getAccount = null;


        try {
            connection = connectionMaker.makeConnection();
            String query = "SELECT * FROM account WHERE id = ? ";
            System.out.println("2 > "+query);
            statement = connection.prepareStatement(query);
            statement.setLong(1,id);
            System.out.println("3 > "+statement.toString());
            resultSet = statement.executeQuery();
            System.out.println("4 > "+resultSet.toString());

            if(!resultSet.next()) return getAccount;

            getAccount = new Account(
                    resultSet.getLong("id"),
                    resultSet.getString("nickname"),
                    resultSet.getString("email"),
                    resultSet.getObject("join_at", LocalDate.class),
                    resultSet.getObject("password_update_date",LocalDate.class),
                    resultSet.getBoolean("alarm_change_password"),
                    resultSet.getBoolean("receive_email")
            );
        }catch (ClassNotFoundException | SQLException e1) {
            System.out.println(" error of account findById ");
        } finally {
            if (statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
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

        return getAccount;
    }

    @Override
    public List<Account> findByAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Account> accountList = new CopyOnWriteArrayList<>();
        try {
            connection = connectionMaker.makeConnection();
            String query = "SELECT * FROM account ORDER BY account_id ASC;";

            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Account getAccount = getAccount = new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("nickname"),
                        resultSet.getString("email"),
                        resultSet.getObject("join_at", LocalDate.class),
                        resultSet.getObject("password_update_date",LocalDate.class),
                        resultSet.getBoolean("alarm_change_password"),
                        resultSet.getBoolean("receive_email")
                );
                accountList.add(getAccount);
            }

        }catch (ClassNotFoundException | SQLException e1) {
            System.out.println(" error of account list");
        } finally {
            if (statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
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
        return accountList;
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

        }  catch (ClassNotFoundException | SQLException e1) {
            System.out.println("account existByEmail 에러");
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

        }  catch (ClassNotFoundException | SQLException e1) {
            System.out.println("account existByNickname 에러");
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
        return result;
    }

    @Override
    public Integer deleteById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = connectionMaker.makeConnection();
            String sql = "DELETE FROM account WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            result = statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e1) {
            System.out.println("error : account deleteById ");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // here not throw out, complete for connection close
                }
            }
            if ( connection != null ) {
                try {
                    connection.close();
                    System.out.println("account deleteById OK");
                } catch (SQLException e) {

                }
            }
        }
        return result;
    }
}
