package org.freeuni.homeworker.server.controller.session;

import org.junit.Test;

public class SessionManagerTest {

	@Test
	public void getSession() {
		SessionManager sessionManager = new SessionManager();
		String sessionId = sessionManager.createNewSession();
		System.out.println(sessionManager.getSession(sessionId));
	}
}