package org.freeuni.homeworker.server.model.source;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("WeakerAccess")
public class ConnectionPool {

	// data structure in which actual connection pool is located
	private BlockingQueue<Connection> connectionsPool;

	public ConnectionPool(List<Connection> connections) {
		if (connections != null && connections.size() > 0) { // assert that number of connections passed is more than zero if that's not so this class has no function.
			connectionsPool = new ArrayBlockingQueue<>(connections.size()); //initialize new pool
			connectionsPool.addAll(connections); // use add all because there is no constraint with queue capacity it is exactly the size of the list passed
		} else {
			throw new IllegalStateException("At least one connection should be passed to UserManagerSQL"); // throw new exception to say that illegal state was detected
		}
	}

	// takes a connection if it is available if not blocks before recourse is available
	public Connection acquireConnection() throws InterruptedException {
		return connectionsPool.take();
	}

	// method to return connection back to pool only connection retrieved from pool should be back to pool if not illegal state may occur
	public void putBackConnection(Connection connection) {
		connectionsPool.add(connection);
	}
}
