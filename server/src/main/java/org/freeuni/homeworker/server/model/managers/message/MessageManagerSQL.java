package org.freeuni.homeworker.server.model.managers.message;

import org.freeuni.homeworker.server.model.objects.message.Message;

import java.util.List;

public class MessageManagerSQL implements MessageManager {



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
