package org.freeuni.homeworker.server.model.managers.users;

import org.freeuni.homeworker.server.model.managers.AbstractManager;
import org.freeuni.homeworker.server.model.objects.user.User;

import java.util.List;

/*
 * This interface is to manage users it is responsible for
 * saving and reading users form the database of choice.
 */
public interface UserManager extends AbstractManager {

    boolean addUser(User user); // simple add user method

    User getUserById(long id); // simple user exists method that checks if there is such user in the database

    User getUserByEmail(String email);

    List<User> getUsers();
}
