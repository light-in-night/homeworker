package org.freeuni.homeworker.server.controller.session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

	private ConcurrentHashMap<String, Session> sessionMap;

	public SessionManager() {
		sessionMap = new ConcurrentHashMap<>();
	}

	public String createNewSession() {
		String newUserId = UUID.randomUUID().toString();
		Session newSession = new Session();
		sessionMap.put(newUserId, newSession);
		return newUserId;
	}

	public Session getSession(String sessionId) {
		return sessionMap.computeIfAbsent(sessionId, (session) -> sessionMap.get(session));
	}
}
