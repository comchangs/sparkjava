package com.sh.pojo.config.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {

    // 규약
    public Connection makeConnection() throws ClassNotFoundException, SQLException;


}
