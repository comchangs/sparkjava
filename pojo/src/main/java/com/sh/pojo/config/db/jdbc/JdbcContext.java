package com.sh.pojo.config.db.jdbc;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.exception.DataAccessEsception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcContext {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConnectionMaker connectionMaker;
    public JdbcContext(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    private Boolean executeUpdate(String query, MakePrepareStatement makePrepareStatement) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            statement = connection.prepareStatement(query);

            makePrepareStatement.setParameters(statement);
            int result = statement.executeUpdate();

            //either (1) the row count for INSERT, UPDATE, or DELETE statements or (2) 0 for SQL statements that return nothing
            return result>=0;
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

    public Boolean executeUpdateInContext(String query,  Object... prepareStatementParameters) {
        MakePrepareStatement makePrepareStatement = makePrepareStatement(prepareStatementParameters);
        return executeUpdate(query, makePrepareStatement);
    }

    public <T> T executeQuery(String query, MapperRow<T> mapperRow, MakePrepareStatement makePrepareStatement) {
        List<T> queryList = executeQueryList(query, mapperRow, makePrepareStatement);
        if(queryList == null) return null;
        return queryList.get(0);
    }

    public <T> List<T> executeQueryList(String query, MapperRow<T> mapperRow, MakePrepareStatement makePrepareStatement) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> accountList = new CopyOnWriteArrayList<>();

        try {
            connection = connectionMaker.makeConnection();
            statement = connection.prepareStatement(query);

            makePrepareStatement.setParameters(statement);

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                accountList.add(mapperRow.mapper(resultSet));
            }
            return accountList;

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

    }

    public <T> T executeQueryInContext(String query, MapperRow<T> mapperRow, Object... prepareStatementParameters) {
        MakePrepareStatement makePrepareStatement = makePrepareStatement(prepareStatementParameters);
        return executeQuery(query, mapperRow, makePrepareStatement);
    }

    public <T> List<T> executeQueryListInContext(String query, MapperRow<T> mapperRow, Object... prepareStatementParameters) {
        MakePrepareStatement makePrepareStatement = makePrepareStatement(prepareStatementParameters);
        return executeQueryList(query, mapperRow, makePrepareStatement);
    }

    private MakePrepareStatement makePrepareStatement(Object... psParameters) {
        return new MakePrepareStatement() {
            @Override
            public void setParameters(PreparedStatement statement) throws SQLException {
                int idx = 0;
                for (Object params : psParameters) {
                    statement.setObject(++idx, params);
                }
            }
        };
    }


}
