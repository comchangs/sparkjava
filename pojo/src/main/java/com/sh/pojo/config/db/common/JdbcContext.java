package com.sh.pojo.config.db.common;

import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.exception.DataAccessEsception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private final ConnectionMaker connectionMaker;

    public JdbcContext(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    private Boolean executeUpdate(String query, Object... prepareStatementParameters) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionMaker.makeConnection();
            statement = connection.prepareStatement(query);

            executeUpdateInJdbc(query,statement);

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

    public Boolean executeUpdateInJdbc(String query,  Object... prepareStatementParameters) {
        MakePrepareStatement makePrepareStatement = makePrepareStatement(prepareStatementParameters);
        return executeUpdate(query, makePrepareStatement);
    }

    private MakePrepareStatement makePrepareStatement(Object[] psParameters) {
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
