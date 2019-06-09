package org.freeuni.homeworker.server.model.managers.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.objects.user.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class is implementation of
 * @code UserManager interface
 */
public class UserManagerSQL implements UserManager {

	private static Logger log = LoggerFactory.getLogger(UserManagerSQL.class);

	@SuppressWarnings("SqlResolve")
	private static final String ADD_USER = "INSERT INTO users (first_name, last_name, gender, email, password) VALUES (?,?,?,?,?)"; // query literal used for adding new user into the  database.

	@SuppressWarnings("SqlResolve")
	private static final String FIND_USER_BY_ID = "SELECT id, first_name, last_name, gender, email, password FROM users WHERE id = ?"; // query literal used for querying users by id

	@SuppressWarnings("SqlResolve")
	private static final String FIND_USER_BY_EMAIL = "SELECT id, first_name, last_name, gender, email, password FROM users WHERE email = ?"; // query literal used for querying users by email

	// connection pool
	private ConnectionPool connectionPool;

	public UserManagerSQL(ConnectionPool connectionPool) {
		if (connectionPool != null) {
			this.connectionPool = connectionPool;
		} else {
			throw new IllegalStateException("Illegal state during creation of UserManagerSQL class.");
		}
	}

	@Override
    public boolean addUser(User user) {
    	if (user.isValid()) {
    		user.setPassword(DigestUtils.sha256Hex(user.getPassword())); // encrypt received user password before saving it into database

    		Connection connection;

    		try {
				connection = connectionPool.acquireConnection(); // try to acquire connection
			} catch (InterruptedException e) {
    			log.info("Thread was interrupted.", e);  // if thread is interrupted return false
				return false;
			}

    		try {
				PreparedStatement insertStatement = connection.prepareStatement(ADD_USER);
				insertStatement.setString(1, user.getFirstName());
				insertStatement.setString(2, user.getLastName()); // insert new row
				insertStatement.setString(3, user.getGender());
				insertStatement.setString(4, user.getEmail());
				insertStatement.setString(5, user.getPassword());
				insertStatement.executeUpdate();
			} catch (SQLException e) {
				log.info("Sql exception was cached.", e);
				connectionPool.putBackConnection(connection); // put back  connection if there is an exception
				return false;
			}

    		connectionPool.putBackConnection(connection); // return connection to pool
    		return true;
		}  else {
    		log.info("Received user was not a valid one.");
    		log.info(user.toString());
    		return false;
		}
	}

    @Override
    public User getUserById(long id) {
		Connection connection;

		try {
			connection = connectionPool.acquireConnection();
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
			log.info("Sql exception was caught.", e);
			connectionPool.putBackConnection(connection);
			return null;
		}

		connectionPool.putBackConnection(connection);
		return UserFactory.fromResultSet(resultSet);
	}

	@Override
	public User getUserByEmail(String email) {
		Connection connection;

		try {
			connection = connectionPool.acquireConnection();
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
			connectionPool.putBackConnection(connection);
			return null;
		}

		connectionPool.putBackConnection(connection);
		return UserFactory.fromResultSet(resultSet);
	}
}
