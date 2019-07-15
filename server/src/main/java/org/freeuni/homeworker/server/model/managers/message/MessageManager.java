package org.freeuni.homeworker.server.model.managers.message;

import org.freeuni.homeworker.server.model.objects.message.Message;

import java.util.List;

public interface MessageManager {

	List<Message> getChatOf(long firstUserId, long secondUserId);

	List<Message> getChatOfAfterMessageId(long firstUserId, long secondUserId, long minimalMessageId);

	void addMessage(Message message);

}
