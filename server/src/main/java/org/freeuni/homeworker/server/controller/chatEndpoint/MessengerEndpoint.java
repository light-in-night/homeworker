package org.freeuni.homeworker.server.controller.chatEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{username}")
public class MessengerEndpoint {

	private static final Logger log = LoggerFactory.getLogger(MessengerEndpoint.class);

	private static final ConcurrentHashMap<String, Session> chatSessions = new ConcurrentHashMap<>();

	@OnOpen
	public void connectionOpened(Session session, EndpointConfig endpointConfig) throws IOException {
		log.info("User was connected");
	}

	@OnMessage
	public String handleTextMessage(Session session, String s) throws IOException {
		log.info("Received message: " + s);
		session.getBasicRemote().sendText("yle");
		return s;
	}


}
