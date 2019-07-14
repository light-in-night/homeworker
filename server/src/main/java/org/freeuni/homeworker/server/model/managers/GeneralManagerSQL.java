package org.freeuni.homeworker.server.model.managers;

import org.freeuni.homeworker.server.model.source.ConnectionPool;

/**
 * Abstract manager for all your Abstraction needs.
 *
 * Author: Guga Tkesheladze
 */
public class GeneralManagerSQL {

    protected ConnectionPool connectionPool;

    public GeneralManagerSQL(ConnectionPool connectionPool) {
        if(connectionPool != null) {
            this.connectionPool = connectionPool;
        } else {
            throw new IllegalStateException("Illegal state during creation of " + this.getClass().getName() + " class.");
        }
    }

    /**
     * this method releases all the connection resources
     * of the manager.
     */
    public void destroyManager() {
        connectionPool.destroy();
    }
}
