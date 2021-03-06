package org.freeuni.homeworker.server.model.source;

import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Connection pool holds connections and
 * is thread-safe.
 * @author Guga Tkesheladze
 */
@SuppressWarnings("WeakerAccess")
public class ConnectionPool {

	// data structure in which actual connection pool is located
	private BlockingQueue<Connection> connectionsPool;

	private boolean destroying;

	private int numberOfInitialConnections;

	private static Logger log = LoggerFactory.getLogger(UserManagerSQL.class);

	/**
	 * Initializes a new connection pool with given list of connections.
	 * @param connections @code List<Connection>
	 */
	public ConnectionPool(List<Connection> connections) {
		if (connections != null && connections.size() > 0) { // assert that number of connections passed is more than zero if that's not so this class has no function.
			connectionsPool = new ArrayBlockingQueue<>(connections.size()); //initialize new pool
			connectionsPool.addAll(connections); // use add all because there is no constraint with queue capacity it is exactly the size of the list passed
			destroying = false;
			numberOfInitialConnections = connections.size();
		} else {
			throw new IllegalStateException("At least one connection should be passed to UserManagerSQL"); // throw new exception to say that illegal state was detected
		}
	}

	/**
	 *
	 * takes a connection if it is available if not blocks before recourse is available
 	 */
	public Connection acquireConnection() throws InterruptedException {
		if (!destroying) {
			return connectionsPool.take();
		}
		return null;
	}

	/**
	 * method to return connection back to pool only connection retrieved from pool should be back to pool if not illegal state may occur
	 * @param connection connection to return this must be ALWAYS called after the aqureConnection() method.
	 */
	public void putBackConnection(Connection connection) {
		connectionsPool.add(connection);
	}

	/**
	 * Destroy method must be called whenever the object is to be deleted.
	 */
	public void destroy() {
		destroying = true;
		while (true) {
			if (connectionsPool.size() == numberOfInitialConnections) {
				for (int i = 0; i < numberOfInitialConnections; i++) {
					try {
						connectionsPool.take().close();
					} catch (Exception e) {
						log.error("Error occurred during destroying connection pool", e);
					}
				}
				break;
			}
			sleepThreadSafely(500); //TODO : UNTESTABLE!
		}
	}

	private void sleepThreadSafely(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.info("Thread was interrupted, while sleeping.", e);
		}
	}
}
