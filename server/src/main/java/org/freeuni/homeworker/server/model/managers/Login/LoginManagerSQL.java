package org.freeuni.homeworker.server.model.managers.Login;

import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.objects.user.UserFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManagerSQL implements LoginManager{

	private final Logger log = LoggerFactory.getLogger(LoginManagerSQL.class);

	private final ConnectionPool connectionsPool;

	private final String SELECT_USER = "SELECT * FROM users WHERE email = ?";

	public LoginManagerSQL(ConnectionPool connectionsPool){
		this.connectionsPool = connectionsPool;
	}



	@Override
	public User getUserByEmail(String email) {
		User user = null;
		Connection connection = null;
		try {
			connection = connectionsPool.acquireConnection();
			user = getUserFromDatabase(email, connection);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("Interrupted Thread");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Syntax Error");
		} finally {
			if(connection != null) {
				connectionsPool.putBackConnection(connection);
			}
		}
		return user;
	}

	private User getUserFromDatabase(String email, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);
		preparedStatement.setString(1, email);
		preparedStatement.executeQuery();
		ResultSet resultSet = preparedStatement.getResultSet();
		return UserFactory.fromResultSet(resultSet);
	}
}
