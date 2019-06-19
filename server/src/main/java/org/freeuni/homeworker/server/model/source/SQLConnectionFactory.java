package org.freeuni.homeworker.server.model.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a connection.
 * Fires the first statement of using the default database.
 */
@SuppressWarnings("WeakerAccess")
public class SQLConnectionFactory {

    /**
     * Creates a new connection.
     * executes the first statement of 'USE'
     * @return new connection
     * @throws SQLException if sql connection attempt failed
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
        connection.createStatement().executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
        return connection;
    }

    /**
     * Creates a list of new connections
     * @param numberOfConnections number of connections to return
     * @return list of new connections
     * @throws SQLException if sql connection attempt failed
     */
    public static List<Connection> getConnectionList(int numberOfConnections) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < numberOfConnections; i++) {
            connections.add(getConnection());
        }
        return connections;
    }
}
