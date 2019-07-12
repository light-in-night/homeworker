package org.freeuni.homeworker.server.model.managers.Login;

import org.freeuni.homeworker.server.model.objects.user.User;

import java.sql.SQLException;

public interface LoginManager {

	User getUserByEmail(String email) throws InterruptedException, SQLException;

}
