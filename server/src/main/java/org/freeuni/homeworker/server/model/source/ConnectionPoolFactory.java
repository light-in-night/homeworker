package org.freeuni.homeworker.server.database.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ConnectionPoolFactory {

	private static Logger log = LoggerFactory.getLogger(ConnectionPoolFactory.class);

	public static ConnectionPool buildConnectionPool(int size) {
		try {
			List<Connection> connections = SQLConnectionFactory.getConnectionList(size);
			return new ConnectionPool(connections);
		} catch (SQLException e) {
			log.info("Error occurred during creation of ConnectionPool class.", e);
		}
		return null;
	}

	public static ConnectionPool buildConnectionPool(List<Connection> connections) {
		return new ConnectionPool(connections);
	}
}
