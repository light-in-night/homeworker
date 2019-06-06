package org.freeuni.homeworker.server.model.managers.Login;

import org.freeuni.homeworker.server.model.objects.user.User;

public interface LoginManager {

	User getUserByEmail(String email);

}
