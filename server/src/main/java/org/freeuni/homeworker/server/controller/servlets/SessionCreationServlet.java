package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.controller.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.session.SessionIdTransferObject;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SessionCreationServlet", urlPatterns = {"/createSession"})
public class SessionCreationServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		ServletUtils.setJSONContentType(resp);
		SessionManager sessionManager = (SessionManager) getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		String sessionId = sessionManager.createNewSession();
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		resp.getWriter().write(objectMapper.writeValueAsString(new SessionIdTransferObject(sessionId)));
	}
}
