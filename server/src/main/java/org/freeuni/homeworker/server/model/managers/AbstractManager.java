package org.freeuni.homeworker.server.model.managers;

/**
 * Abstract manager for all your Abstraction needs.
 *
 * Author: Givi Khartishvili
 */
public interface AbstractManager {

    /**
     * this method releases all the connection resources
     * of the manager.
     */
    void destroyManager();
}
