package org.freeuni.homeworker.server.model.managers.users;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.objects.user.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class is implementation of
 * @code UserManager interface
 */
public class UserManagerSQL extends GeneralManagerSQL implements UserManager {

	private static Logger log = LoggerFactory.getLogger(UserManagerSQL.class);

	private static final String ADD_USER = "INSERT INTO users (first_name, last_name, gender, email, password) VALUES (?,?,?,?,?);"; // query literal used for adding new user into the  database.

	private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?;"; // query literal used for querying users by id

	private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?;"; // query literal used for querying users by email

	private static final String GET_ALL_USERS = "SELECT * FROM users;";

	public UserManagerSQL(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
    public void addUser(User user) throws SQLException, InterruptedException {
//    	if (user.isValid()) {
//    		user.setPassword(DigestUtils.sha256Hex(user.getPassword())); // encrypt received user password before saving it into database
//		This must happen before calling UserManager.addUser.
//		Because it is not UserManager's job to encrypt something.
//		also, this makes testing a lot more harder.
		Connection connection = null;

		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName()); // insert new row
			preparedStatement.setString(3, user.getGender());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.executeUpdate();
		} finally {
			if(connection != null) {
				connectionPool.putBackConnection(connection); // put back  connection if there is an exception
			}
		}
	}

    @Override
    public User getUserById(long id) throws InterruptedException, SQLException {
		Connection connection = null;
		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
			preparedStatement.setLong(1, id);
			return UserFactory.fromResultSet(preparedStatement.executeQuery());
		} finally {
			if (connection != null) {
				connectionPool.putBackConnection(connection);
			}
		}
	}

	@Override
	public User getUserByEmail(String email) throws InterruptedException, SQLException {
		Connection connection = null;
		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			return UserFactory.fromResultSet(resultSet);
		} finally {
			if(connection != null) {
				connectionPool.putBackConnection(connection);
			}
		}
	}


	@Override
	public List<User> getUsers(User filter) throws InterruptedException, SQLException {
		String getUsers = generateSQLStringForSelect(filter);

		return getUsers(getUsers);
	}

	protected List<User> getUsers(String getUsers) throws InterruptedException, SQLException {
		Connection connection = null;
		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(getUsers);
			ResultSet resultSet = preparedStatement.executeQuery();
			return UserFactory.usersFromResultSet(resultSet);
		} finally {
			if (connection != null) {
				connectionPool.putBackConnection(connection);
			}
		}
	}

	/**
	 * Generates SQL Select String
	 * @param filter filter Object Based On Filter Is Made
	 * @return SQL String
	 */
	private String generateSQLStringForSelect(User filter) {
		StringBuilder getUsers = new StringBuilder("SELECT * FROM users WHERE 1 = 1 ");
		Map<String, Object> properties = new HashMap<>();


		if(filter.getId() != null){
			properties.put("id", filter.getId());
		}
		if(filter.getFirstName() != null){
			properties.put("firstName", filter.getFirstName());
		}
		if(filter.getLastName() != null){
			properties.put("lastName", filter.getLastName());
		}
		if(filter.getGender() != null){
			properties.put("gender", filter.getGender());
		}
		if(filter.getEmail() != null){
			properties.put("email", filter.getEmail());
		}
		if(filter.getPassword() != null){
			properties.put("password", filter.getPassword());
		}
		if(filter.getKarma() != null){
			properties.put("karma", filter.getKarma());
		}
		for(String elem : properties.keySet()){
			getUsers.append("AND " + elem + " = " + properties.get(elem) + " ");
		}
		getUsers.append(";");
		return getUsers.toString();
	}


	/**
	 * TODO : implement this!
	 * @param newUser new user object. only user id is same.
	 */
	@Override
	public void updateUser(User newUser) throws InterruptedException, SQLException {
		throw new UnsupportedOperationException();
	}
}
