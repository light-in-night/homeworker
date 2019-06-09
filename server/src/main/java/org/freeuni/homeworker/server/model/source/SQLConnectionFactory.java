package org.freeuni.homeworker.server.model.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SQLConnectionFactory {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
        connection.createStatement().executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
        return connection;
    }

    public static List<Connection> getConnectionList(int numberOfConnections) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < numberOfConnections; i++) {
            connections.add(getConnection());
        }
        return connections;
    }
}
