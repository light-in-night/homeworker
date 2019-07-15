package org.freeuni.homeworker.server.model.managers.message;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.message.Message;
import org.freeuni.homeworker.server.model.objects.message.MessageFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageManagerSQL extends GeneralManagerSQL implements MessageManager {

	private static final String QUERY_MESSAGES_OF = "SELECT id, senderId, receiverId, message, sendTime FROM messages WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?) ";

	private static final String ADD_MESSAGE = "INSERT INTO messages(senderId, receiverId, message, sendTime)" +
			"VALUES (?,?,?,?)";

	public MessageManagerSQL(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public List<Message> getChatOf(long firstUserId, long secondUserId) {
		Connection connection = null;
		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_MESSAGES_OF);
			preparedStatement.setLong(1, firstUserId);
			preparedStatement.setLong(2, secondUserId);
			preparedStatement.setLong(3, secondUserId);
			preparedStatement.setLong(4, firstUserId);
			ResultSet resultSet = preparedStatement.executeQuery();
			return MessageFactory.messagesFromResultSet(resultSet);
		} catch (InterruptedException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connectionPool.putBackConnection(connection);
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Message> getChatOfAfterMessageId(long firstUserId, long secondUserId, long minimalMessageId) {
		return null;
	}

	@Override
	public void addMessage(Message message) {
		Connection connection = null;
		try {
			connection = connectionPool.acquireConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(ADD_MESSAGE);
			preparedStatement.setLong(1, message.getSenderId());
			preparedStatement.setLong(2, message.getReceiverId());
			preparedStatement.setString(3, message.getMessage());
			preparedStatement.setDate(4, new java.sql.Date(message.getSendTime().getTime()));
			preparedStatement.executeUpdate();
		} catch (InterruptedException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connectionPool.putBackConnection(connection);
			}
		}
	}
}
