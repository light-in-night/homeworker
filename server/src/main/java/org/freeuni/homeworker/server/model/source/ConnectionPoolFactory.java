package org.freeuni.homeworker.server.model.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Static class for creating connectionPool
 * @author Guga Tkesheladze
 */
public class ConnectionPoolFactory {

	private static Logger log = LoggerFactory.getLogger(ConnectionPoolFactory.class);

	/**
	 * Makes a new ConnectionPool with given pool size
	 * @param size pool size (no. of connection)
	 * @return new connectionPool object
	 */
	public static ConnectionPool buildConnectionPool(int size) {
		try {
			List<Connection> connections = SQLConnectionFactory.getConnectionList(size);
			return new ConnectionPool(connections);
		} catch (SQLException e) {
			log.info("Error occurred during creation of ConnectionPool class.", e);
		}
		return null;
	}

	/**
	 * Returns a new connetion pool with given list of connections.
	 * @param connections list of connection in the pool
	 * @return new connectionPool object
	 */
	public static ConnectionPool buildConnectionPool(List<Connection> connections) {
		return new ConnectionPool(connections);
	}
}
