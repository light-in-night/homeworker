package org.freeuni.homeworker.server.model.managers.message;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.message.Message;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.util.List;

public class MessageManagerSQL extends GeneralManagerSQL implements MessageManager {

	public MessageManagerSQL(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public List<Message> getChatOf(long firstUserId, long secondUserId) {
		return null;
	}

	@Override
	public List<Message> getChatOfAfterMessageId(long firstUserId, long secondUserId, long minimalMessageId) {
		return null;
	}

	@Override
	public void addMessage(Message message) {

	}
}
