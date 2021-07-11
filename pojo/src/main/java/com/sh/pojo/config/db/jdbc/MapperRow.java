package com.sh.pojo.config.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperRow<T> {
    T mapper(ResultSet resultSet) throws SQLException;
}
