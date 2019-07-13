package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author : Torike Kechakhmadze, Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "LogoutServlet", urlPatterns = "/hasSession/isLoggedIn/logout")
public class LogoutServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(LogoutServlet.class);

	/**
	 * Marks SessionId as logged out.
	 * If it is already logged out, this does nothing.
	 * <p>
	 * Reads :
	 * <p>
	 * Returns :
	 * {
	 * STATUS : OK | ERROR,
	 * ERROR_MESSAGE
	 * }
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(response);
		ServletUtils.setJSONContentType(response);

		SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		ObjectNode responseRoot = objectMapper.createObjectNode();

		try {
			String sessionID = request.getHeader(ContextKeys.SESSION_ID);
			Session session = sessionManager.getSession(sessionID);
			sessionManager.logout(sessionID, session.getUserId());
			JacksonUtils.addStatusOk(responseRoot);
		} catch (Exception e) {
			JacksonUtils.addStatusError(responseRoot, e.getMessage());
			log.error("Error occurred during logout.", e);
		}
		response.getWriter().write(responseRoot.toString());
	}
}
