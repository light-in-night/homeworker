package org.freeuni.homeworker.server.database.managers;

import org.freeuni.homeworker.server.model.user.User;

/*
 * This interface is to manage users it is responsible for
 * saving and reading users form the database of choice.
 */
public interface UserManager {

    boolean addUser(User user); // simple add user method

    User getUserById(long id); // simple user exists method that checks if there is such user in the database

    User getUserByEmail(String email);
}
