package org.freeuni.homeworker.server.controller.chatEndpoint;

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

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@ServerEndpoint(value = "/chat/{username}")
public class MessengerEndpoint {

	private static final Logger log = LoggerFactory.getLogger(MessengerEndpoint.class);

	private static final ConcurrentHashMap<Long, Session> userSessions = new ConcurrentHashMap<>();

	private static final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final MessageManager messageManager = new MessageManagerSQL(ConnectionPoolFactory.buildConnectionPool(5));

	static {
		Thread thread = new Thread(() -> {
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
					if (receiverSession != null) {
						receiverSession.getBasicRemote().sendText(objectMapper.writeValueAsString(currMessage));
					} else {
						log.info("Receiver with id of:" + currMessage.getReceiverId() +" is logged out.");
					}
				} catch (InterruptedException | IOException e) {
					log.error("Error occurred during handling new messages.", e);
				}
			}
		});
		thread.start();
	}

	@OnOpen
	public void connectionOpened(Session session, EndpointConfig endpointConfig) throws IOException {
		if (!validateSession(getSenderSessionId(session), getParticipantId(session, ParticipantType.SENDER))) {
			session.close();
		} else {
			userSessions.put(getParticipantId(session, ParticipantType.SENDER), session);
			List<Message> messages = messageManager.getChatOf(getParticipantId(session, ParticipantType.SENDER), getParticipantId(session, ParticipantType.RECEIVER));
			session.getBasicRemote().sendText(objectMapper.writeValueAsString(messages));
		}
	}

	@OnMessage
	public void handleTextMessage(Session session, String s) throws IOException {
		log.info("Received message: " + s);
		try {
			Message message = objectMapper.readValue(s, Message.class);
			messageManager.addMessage(message);
			messageQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@OnClose
	public void socketClosed(Session session) {

	}



	private long getParticipantId(Session session, ParticipantType participantType) {
		String url = session.getRequestURI().toString();
		String[] splitRes = url.split("/");
		String participantId = splitRes[splitRes.length - 1].split("!")[participantType == ParticipantType.SENDER ? 0 : 1];
		return Long.parseLong(participantId);
	}

	private String getSenderSessionId(Session session) {
		String url = session.getRequestURI().toString();
		String[] splitRes = url.split("/");
		return splitRes[splitRes.length - 1].split("!")[2];
	}

	private boolean validateSession(String sessionId, long id) {
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
			e.printStackTrace();
		}
		return false;
	}


}
