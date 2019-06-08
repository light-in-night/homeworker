package org.freeuni.homeworker.server.model.user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {

	public static User fromResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return null;
		}

		try {

			resultSet.next();
			User user = new User();
			user.setId(resultSet.getLong(1));
			user.setFirstName(resultSet.getString(2));
			user.setLastName(resultSet.getString(3));
			user.setGender(resultSet.getString(4));
			user.setEmail(resultSet.getString(5));
			user.setPassword(resultSet.getString(6));
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
