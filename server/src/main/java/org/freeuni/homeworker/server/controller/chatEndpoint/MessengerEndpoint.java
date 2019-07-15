package org.freeuni.homeworker.server.controller.chatEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/chat/{username}")
public class MessengerEndpoint {

	private static final Logger log = LoggerFactory.getLogger(MessengerEndpoint.class);

	private static final ChatManager chatManager = ChatManager.getInstance();

	@OnOpen
	public void connectionOpened(Session session, EndpointConfig endpointConfig) throws IOException {
		try {
			if (!chatManager.validateSession(chatManager.getSenderSessionId(session), chatManager.getParticipantId(session, ParticipantType.SENDER))) {
				closeConnection(session);
			} else {
				chatManager.addUserSession(session);
				session.getBasicRemote().sendText(chatManager.getChatJsonsOf(session));
			}
		} catch (NumberFormatException e) {
			closeConnection(session);
		}
	}

	@OnMessage
	public void handleTextMessage(Session session, String s) throws IOException {
		log.info("Received message: " + s);
		try {
			chatManager.registerMessage(s);
		} catch (InterruptedException e) {
			log.error("Error occurred during registration of message.", e);
		}
	}


	@OnClose
	public void socketClosed(Session session) {
		chatManager.logOut(session);
	}

	@OnError
	public void onError(Session session, Throwable th) {
	}


	private void closeConnection(Session session) throws IOException {
		CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.getCloseCode(CloseReason.CloseCodes.VIOLATED_POLICY.getCode()), "Access was illegal");
		session.close(closeReason);
	}



}
