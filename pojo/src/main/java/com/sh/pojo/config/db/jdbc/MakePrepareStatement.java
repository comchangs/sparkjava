package com.sh.pojo.config.db.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface MakePrepareStatement {
    void setParameters(PreparedStatement statement) throws SQLException;

}
