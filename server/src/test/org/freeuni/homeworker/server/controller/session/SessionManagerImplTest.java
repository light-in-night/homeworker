package org.freeuni.homeworker.server.controller.session;

import org.freeuni.homeworker.server.model.managers.session.SessionManagerImpl;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

public class SessionManagerImplTest {

	@Test
	public void getSession() {
		SessionManager sessionManager = new SessionManagerImpl();
		String sessionId = sessionManager.createNewSession();
	}
}