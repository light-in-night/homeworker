package org.freeuni.demo.server.database.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnectionFactory {

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
        connection.createStatement().executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME + ";");
        return connection;
    }
}
