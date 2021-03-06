package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.freeuni.homeworker.server.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * Author : Guram Tkesheladze, Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "SessionServlet", urlPatterns = {"/sessions"})
public class SessionServlet extends HttpServlet {

	/**
	 * This method returns if the session id is active in the server
	 * if the session id key is invalid it returns that.
	 * should pass id as a parameter
	 * Reads :
	 * 	?sessionId=1
	 *
	 * Returns :
	 * {
	 * 	"isValid" : "true"
	 * }
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		ServletUtils.setJSONContentType(resp);

		ObjectMapper objectMapper = (ObjectMapper) req.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		SessionManager sessionManager = (SessionManager) req.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

		String sessionId = req.getParameter(ContextKeys.SESSION_ID);
		ObjectNode node = objectMapper.createObjectNode();
		if (!StringUtils.isNotBlank(sessionId)
				|| !sessionManager.hasSession(sessionId)) {
			node.put("isValid", false);
		} else {
			Session session = sessionManager.getSession(sessionId);
			node.put("isValid", true);
			node.put("userId", session.getUserId());
		}
		resp.getWriter().write(node.toString());
	}

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

		ObjectMapper objectMapper = (ObjectMapper) req.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		SessionManager sessionManager = (SessionManager) req.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put(ContextKeys.SESSION_ID, sessionManager.createNewSession());
		JacksonUtils.addStatusOk(objectNode);

		resp.getWriter().write(objectNode.toString());
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		resp.setStatus(200);
	}
}
