package org.freeuni.homeworker.server.model.source.timedSource;

import java.sql.Connection;

public class TimedConnection {
    private final long start;
    private Connection connection;

    public TimedConnection(Connection connection) {
        start = System.currentTimeMillis();
        this.connection = connection;
    }

    public long milisSinceBorn() {
        return System.currentTimeMillis() - start;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
