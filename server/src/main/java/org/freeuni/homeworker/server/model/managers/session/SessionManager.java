package org.freeuni.homeworker.server.model.managers.session;

import org.freeuni.homeworker.server.model.objects.session.Session;

import java.util.Map;
import java.util.Set;

public interface SessionManager {

    /**
     * Creates a new session and saves it with an empty userId.
     * To register a user with it, use SessionManager.login method.
     * @return sessionId of the newly created session.
     */
    String createNewSession();

    void createCustomSession(String customId);

    boolean isUserLoggedIn(String sessionId);

    void login(String sessionId, long userId);

    void registerUnsuccessfulLogin(String sessionId);

    void logout(String sessionId, long userId);

    Session getSession(String sessionId);

    Set<Map.Entry<String, Session>> getAllSessions();

    boolean hasSession(String sessionId);
}
