package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * Author : Tornike Kechakhmadze, Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "SessionCreationServlet", urlPatterns = {"/sessions"})
public class SessionCreationServlet extends HttpServlet {

	/**
	 * Registers a new session, returns a sessionId.
	 * The latter can be used in order to make a login and
	 * Access /hasSession/* url commands.
	 *
	 * Reads :
	 *
	 * 	Nothing.
	 *
	 * Returns:
	 * {
	 *     STATUS : OK | ERROR
	 *     ERROR_MESSAGE : ""
	 *     sessionId : '1234' (a string)
	 * }
	 * @see LoginServlet LoginServlet
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		ServletUtils.setJSONContentType(resp);

		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		SessionManager sessionManager = (SessionManager) getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put(ContextKeys.SESSION_ID, sessionManager.createNewSession());
		JacksonUtils.addStatusOk(objectNode);

		resp.getWriter().write(objectNode.toString());
	}
}
