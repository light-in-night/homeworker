package org.freeuni.homeworker.server.model.managers.users;

import org.freeuni.homeworker.server.model.objects.user.User;

import java.util.List;

/**
 * This interface is to manage users it is responsible for
 * saving and reading users form the database of choice.
 *
 * Author : Guga Tkesheladze
 *
 */
public interface UserManager {

    /**
     * Adds a new user in the database
     *
     * @param user new user
     * @return true on success, false otherwise
     */
    boolean addUser(User user); // simple add user method

    /**
     * returns a user by id.
     *
     * @param id user id
     * @return user object from database if such exists, null otherwise.
     */
    User getUserById(long id); // simple user exists method that checks if there is such user in the database

    /**
     * returns user by email
     * @param email email of the user
     * @return user object from database if such exists, null otherwise.
     */
    User getUserByEmail(String email);

    /**
     * returns list of all users
     *
     * @return every user in database.
     */
    List<User> getUsers();
}
