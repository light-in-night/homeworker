package org.freeuni.homeworker.server.model.objects.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MessageFactory {

	private static final Logger log = LoggerFactory.getLogger(MessageFactory.class);

	public static List<Message> messagesFromResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return null;
		}

		try {
			List<Message> messages = new ArrayList<>();
			while (resultSet.next()) {
				Message currMessage = messageFromResultSet(resultSet);
				if (currMessage != null) {
					messages.add(currMessage);
				}
			}
			return messages;
		} catch (SQLException e) {
			log.error("Error occurred during getting list of messages from the result set.", e);
		}
		return null;
	}

	public static Message messageFromResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return null;
		}

		try {
			Message message = new Message();
			message.setId(resultSet.getLong("id"));
			message.setSenderId(resultSet.getLong("senderId"));
			message.setReceiverId(resultSet.getLong("receiverId"));
			message.setMessage(resultSet.getString("message"));
			message.setSendTime(resultSet.getDate("sendTime"));
			return message;
		} catch (SQLException e) {
			log.error("Error occurred during getting individual message from result set.", e);
		}
		return null;
	}
}
