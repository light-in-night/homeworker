package org.freeuni.demo.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLSource implements Source {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("");
    }
}
