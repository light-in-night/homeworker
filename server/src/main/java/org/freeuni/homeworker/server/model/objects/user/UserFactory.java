package org.freeuni.homeworker.server.model.objects.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Static factory class. every "new" opeation goes here.
 * can also create lists when needed.
 * @Author Guga Tkesheladze
 */
public class UserFactory {

	private static final Logger log = LoggerFactory.getLogger(UserFactory.class);
	/**
	 * Makes a single object from resultSet.
	 * @param resultSet resultSet of the object
	 * @return object on successful conversion, null otherwise
	 */
	public static User fromResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return null;
		}
		try {
			resultSet.next();
			return getUserFromOneEntry(resultSet);

		} catch (SQLException e) {
			log.error("Error occurred during conversion from result set to user.", e);
			return null;
		}
	}

	/**
	 * Makes a list of objects from resultSet.
	 * @param resultSet resultSet of the object
	 * @return list of objects on successful conversion, null otherwise
	 */
	public static List<User> usersFromResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return null;
		}

		try {
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				User user = getUserFromOneEntry(resultSet);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			log.error("Error occurred during conversion from result set to user.", e);
			return null;
		}
	}

	/**
	 * Makes a single object from resultSet.
	 * @param resultSet resultSet of the object
	 * @return object on successful conversion, null otherwise
	 */
	private static User getUserFromOneEntry(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong(1));
		user.setFirstName(resultSet.getString(2));
		user.setLastName(resultSet.getString(3));
		user.setGender(resultSet.getString(4));
		user.setEmail(resultSet.getString(5));
		user.setPassword(resultSet.getString(6));
		user.setKarma(resultSet.getLong(7));
		return user;
	}

	/**
	 * Wraps user object in objectNode and returns node.
	 *
	 * @param user post object that you want to convert add to given ObjectNode
	 * @param node the given objectNode that you want to use
	 * @return filled node
	 */
	public static ObjectNode toObjectNode(User user, ObjectNode node) {
		node.put("id", user.getId());
		node.put("firstName", user.getFirstName());
		node.put("lastName", user.getLastName());
		node.put("gender", user.getGender());
		node.put("email", user.getEmail());
		node.put("password", user.getPassword());
		node.put("karma", user.getKarma());
		return node;
	}

}
