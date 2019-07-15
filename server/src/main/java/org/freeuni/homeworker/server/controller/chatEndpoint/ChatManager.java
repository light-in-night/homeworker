package org.freeuni.homeworker.server.controller.chatEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.freeuni.homeworker.server.model.managers.message.MessageManager;
import org.freeuni.homeworker.server.model.managers.message.MessageManagerSQL;
import org.freeuni.homeworker.server.model.objects.message.Message;
import org.freeuni.homeworker.server.model.source.ConnectionPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("WeakerAccess")
public class ChatManager {

	private static ChatManager instance = null;

	private Logger log = LoggerFactory.getLogger(ChatManager.class);

	private ConcurrentHashMap<Long, Session> userSessions;

	private BlockingQueue<Message> messageQueue;

	private ObjectMapper objectMapper;

	private MessageManager messageManager;

	private Worker worker = null;

	public static ChatManager getInstance() {
		if (instance == null) {
			instance = new ChatManager();
		}
		return instance;
	}

	private ChatManager() {
		userSessions = new ConcurrentHashMap<>();
		messageQueue = new ArrayBlockingQueue<>(300);
		objectMapper = new ObjectMapper();
		messageManager = new MessageManagerSQL(ConnectionPoolFactory.buildConnectionPool(5));
		runWorker();
	}

	public void addUserSession(Session session) {
		userSessions.put(getParticipantId(session, ParticipantType.SENDER), session);
	}

	public long getParticipantId(Session session, ParticipantType participantType) {
		String url = session.getRequestURI().toString();
		String[] splitRes = url.split("/");
		String participantId = splitRes[splitRes.length - 1].split("!")[participantType == ParticipantType.SENDER ? 0 : 1];
		if (participantId.equalsIgnoreCase("undefined")) {
			return -1;
		}
		return Long.parseLong(participantId);
	}

	public String getSenderSessionId(Session session) {
		String url = session.getRequestURI().toString();
		String[] splitRes = url.split("/");
		return splitRes[splitRes.length - 1].split("!")[2];
	}

	public boolean validateSession(String sessionId, long id) {
		log.info("calling service to get session.");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet get = new HttpGet("http://127.0.1/sessions?sessionId="+sessionId);
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			JsonNode node = objectMapper.readTree(json);
			if (node.get("isValid").asBoolean()) {
				String userId = node.get("userId").asText();
				return userId.equals(String.valueOf(id));
			}
		} catch (IOException e) {
			log.error("Error occurred during retrieval of user session status.", e);
		}
		return false;
	}

	public String getChatJsonsOf(Session session) throws JsonProcessingException {
		List<Message> messages = messageManager.getChatOf(getParticipantId(session, ParticipantType.SENDER), getParticipantId(session, ParticipantType.RECEIVER));
		return objectMapper.writeValueAsString(messages);
	}

	public void registerMessage(String s) throws InterruptedException, IOException {
		Message message = getMessage(s);
		messageManager.addMessage(message);
		messageQueue.put(message);
	}

	public void logOut(Session session) {
		userSessions.remove(getParticipantId(session, ParticipantType.SENDER));
	}

	public Message getMessage(String s) throws IOException {
		return objectMapper.readValue(s, Message.class);
	}

	public void runWorker() {
		if (worker == null) {
			worker = new Worker();
			worker.start();
		}
	}

	class Worker extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Message currMessage = messageQueue.take();
					long senderId = currMessage.getSenderId();
					Session senderSession = userSessions.get(senderId);

					long receiverId = currMessage.getReceiverId();
					Session receiverSession = userSessions.get(receiverId);
					if (senderSession != null) {
						senderSession.getBasicRemote().sendText(objectMapper.writeValueAsString(currMessage));
					} else {
						log.info("Sender with id of:" + currMessage.getSenderId() +" is logged out.");
					}
					if (receiverSession != null && senderId != receiverId) {
						receiverSession.getBasicRemote().sendText(objectMapper.writeValueAsString(currMessage));
					} else {
						log.info("Receiver with id of:" + currMessage.getReceiverId() +" is logged out.");
					}
				} catch (InterruptedException | IOException e) {
					log.error("Error occurred during handling new messages.", e);
				}
			}
		}
	}

}
