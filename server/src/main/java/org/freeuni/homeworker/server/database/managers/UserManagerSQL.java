package org.freeuni.homeworker.server.database.managers;

import org.apache.commons.codec.digest.DigestUtils;
import org.freeuni.homeworker.server.model.user.User;
import org.freeuni.homeworker.server.model.user.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * This class is implementation of
 * @code UserManager interface
 */
@SuppressWarnings("WeakerAccess")
public class UserManagerSQL implements UserManager {

	private static Logger log = LoggerFactory.getLogger(UserManagerSQL.class);

	@SuppressWarnings("SqlResolve")
	private static final String ADD_USER = "INSERT INTO users (first_name, last_name, gender, email, password) VALUES (?,?,?,?,?)"; // query literal used for adding new user into the  database.

	@SuppressWarnings("SqlResolve")
	private static final String FIND_USER_BY_ID = "SELECT id, first_name, last_name, gender, email, password FROM users WHERE id = ?"; // query literal used for querying users by id

	@SuppressWarnings("SqlResolve")
	private static final String FIND_USER_BY_EMAIL = "SELECT id, first_name, last_name, gender, email, password FROM users WHERE email = ?"; // query literal used for querying users by email

	// blocking queue this will serve as a connection pool for this class its max size will be number of connections that will be passed to it in constructor
	private BlockingQueue<Connection> availableConnections;

	public UserManagerSQL(List<Connection> connections) {
		if (connections != null && connections.size() > 0) { // assert that number of connections passed is more than zero if thats not so this class has no function.
			availableConnections = new ArrayBlockingQueue<>(connections.size()); //initialize new pool
			availableConnections.addAll(connections); // use add all because there is no constraint with queue capacity it is exactly the size of the list passed
		} else {
			throw new IllegalStateException("At least one connection should be passed to UserManagerSQL"); // throw new exception to say that illegal state was detected
		}
	}

	@Override
    public boolean addUser(User user) {
    	if (user.isValid()) {
    		user.setPassword(DigestUtils.sha256Hex(user.getPassword())); // encrypt received user password before saving it into database

    		Connection connection;

    		try {
				connection = availableConnections.take(); // try to acquire connection
			} catch (InterruptedException e) {
    			log.info("Thread was interrupted.", e);  // if thread is interrupted return false
				return false;
			}

    		try {
				PreparedStatement insertStatement = connection.prepareStatement(ADD_USER);
				insertStatement.setString(1, user.getFirstName());
				insertStatement.setString(2, user.getLastName());
				insertStatement.setString(3, user.getGender());
				insertStatement.setString(4, user.getEmail());
				insertStatement.setString(5, user.getPassword());
				insertStatement.executeUpdate();
			} catch (SQLException e) {
				log.info("Sql exception was cached.", e);
				availableConnections.add(connection);
				return false;
			}

    		availableConnections.add(connection);
    		return true;
		}  else {
    		log.info("Received user was not a valid one.");
    		return false;
		}
	}

    @Override
    public User getUserById(long id) {
		Connection connection;

		try {
			connection = availableConnections.take();
		} catch (InterruptedException e) {
			log.info("Thread was interrupted.", e);
			return null;
		}

		ResultSet resultSet;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			log.info("Sql exception was cached.", e);
			availableConnections.add(connection);
			return null;
		}

		availableConnections.add(connection);
		return UserFactory.fromResultSet(resultSet);
	}

	@Override
	public User getUserByEmail(String email) {
		Connection connection;

		try {
			connection = availableConnections.take();
		} catch (InterruptedException e) {
			log.info("Thread was interrupted.", e);
			return null;
		}

		ResultSet resultSet;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			log.info("Sql exception was cached.", e);
			availableConnections.add(connection);
			return null;
		}

		availableConnections.add(connection);
		return UserFactory.fromResultSet(resultSet);
	}
}
