package com.sh.pojo.config.db.common;

import com.sh.pojo.config.db.ConnectionMaker;
import com.sh.pojo.config.db.exception.DataAccessEsception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            if(makePrepareStatement != null) makePrepareStatement.setParameters(statement);
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
        if(prepareStatementParameters==null) return executeUpdate(query, null);
        MakePrepareStatement makePrepareStatement = makePrepareStatement(prepareStatementParameters);
        return executeUpdate(query, makePrepareStatement);
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
