package org.freeuni.homeworker.server.controller.session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("WeakerAccess")
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

	public boolean isUserLoggedIn(String sessionId) {
		AtomicBoolean result =  new AtomicBoolean();
		sessionMap.computeIfPresent(sessionId, (id, session) -> {
			result.set(session.isLoggedIn());
			return session;
		});
		return result.get();
	}

	public Session login(String sessionId, long userId) {
		return sessionMap.computeIfPresent(sessionId, (id, session) -> {
			session.setLoggedIn(true);
			session.setUserId(userId);
			return session;
		});
	}

	public Session registerUnsuccessfulLogin(String sessionId) {
		return sessionMap.computeIfPresent(sessionId, (id, session) -> {
			session.setNumberOfUnSuccessfulAttempts(session.getNumberOfUnSuccessfulAttempts() + 1);
			return session;
		});
	}
}
