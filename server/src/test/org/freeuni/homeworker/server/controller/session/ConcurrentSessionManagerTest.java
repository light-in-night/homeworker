package org.freeuni.homeworker.server.controller.session;

import org.freeuni.homeworker.server.model.managers.session.ConcurrentSessionManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.junit.Test;

public class ConcurrentSessionManagerTest {

	@Test
	public void getSession() {
		SessionManager sessionManager = new ConcurrentSessionManager();
		String sessionId = sessionManager.createNewSession();
	}
}