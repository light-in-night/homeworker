package org.freeuni.demo.server.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Source {
    Connection getConnection() throws SQLException;

}
