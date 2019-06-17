package org.freeuni.homeworker.server.model.source.timedSource;

import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    // data structure in which actual connection pool is located
    private BlockingQueue<TimedConnection> connectionsPool;

    private boolean destroying;

    private int numberOfInitialConnections;

    private static Logger log = LoggerFactory.getLogger(UserManagerSQL.class);

    public ConnectionPool(List<Connection> connections) {

    }

    // takes a connection if it is available if not blocks before recourse is available
    public Connection acquireConnection() throws InterruptedException {
        return null;
    }

    public void putBackConnection(Connection connection) {

    }

    public void destroy() {

    }
}
