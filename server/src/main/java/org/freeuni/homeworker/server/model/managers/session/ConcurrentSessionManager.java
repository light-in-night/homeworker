package org.freeuni.homeworker.server.model.managers.session;

import org.freeuni.homeworker.server.model.objects.session.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrentSessionManager implements SessionManager {
	private Map<String, Session> sessionMap;

	/**
	 * Creates a SessionManager with ConcurrentHashMap.
	 */
	public ConcurrentSessionManager() {
		sessionMap = new ConcurrentHashMap<>();
	}

	/**
	 * Creates a new ConcurrentSessionManager object.
	 *
	 * @param sessionMap this Map <b>MUST BE THREAD SAFE</b>, in order
	 *                    for this class to work well.
	 */
	public ConcurrentSessionManager(Map<String, Session> sessionMap) {
		this.sessionMap = sessionMap;
	}

	/**
	 * Creates a new session and saves it with an empty userId.
	 * To register a user with it, use SessionManager.login method.
	 * @return sessionId of the newly created session.
	 */
	@Override
	public String createNewSession() {
		String newUserId = UUID.randomUUID().toString();
		Session newSession = new Session();
		sessionMap.put(newUserId, newSession);
		return newUserId;
	}

    @Override
    public void createCustomSession(String customId) {
		Session newSession = new Session();
		sessionMap.put(customId, newSession);
    }

    /**
	 *
	 * @param sessionId	String that was returned by some call of SessionManager.createNewSession()
	 * @return true if user is logged in and sessionId is valid, false otherwise.
	 */
	@Override
	public boolean isUserLoggedIn(String sessionId) {
		AtomicBoolean result =  new AtomicBoolean();
		sessionMap.computeIfPresent(sessionId, (id, session) -> {
			result.set(session.isLoggedIn());
			return session;
		});
		return result.get();
	}

	/**
	 *
	 * @param sessionId
	 * @param userId
	 */
	@Override
	public void login(String sessionId, long userId) {
		sessionMap.computeIfPresent(sessionId, (id, session) -> {
			session.setLoggedIn(true);
			session.setUserId(userId);
			session.setLastActivityTime(Date.valueOf(LocalDate.now()));
			return session;
		});
	}

	/**
	 *
	 * @param sessionId
	 * @param userId
	 */
	@Override
	public void logout(String sessionId, long userId) {
		sessionMap.computeIfPresent(sessionId, (id, session) -> {
			session.setLoggedIn(false);
			session.setUserId(userId);
			session.setLastActivityTime(Date.valueOf(LocalDate.now()));
			return session;
		});
	}

	@Override
	public Session getSession(String sessionId) {
		return sessionMap.get(sessionId);
	}

    @Override
    public Set<Map.Entry<String, Session>> getAllSessions() {
		return sessionMap.entrySet();
    }

    @Override
    public boolean hasSession(String sessionId) {
		return sessionMap.containsKey(sessionId);
    }

    /**
	 * Counts number of unsuccessful attempts a login.
	 *
	 * @param sessionId session on which the login is requested.
	 * @return
	 */
	@Override
	public void registerUnsuccessfulLogin(String sessionId) {
		sessionMap.computeIfPresent(sessionId, (id, session) -> {
			session.setNumberOfUnsuccessfulAttempts(session.getNumberOfUnsuccessfulAttempts() + 1);
			return session;
		});
	}
}
