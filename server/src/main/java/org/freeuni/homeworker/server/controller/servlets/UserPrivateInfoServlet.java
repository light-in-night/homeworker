package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This servlet returns users full, private data and information it is only accessible by
 * user's session id.
 */
@WebServlet(name = "UserPrivateInfoServlet", urlPatterns = {"/hasSession/isLoggedIn/privateUserInfo"})
public class UserPrivateInfoServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(UserPrivateInfoServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		ServletUtils.setJSONContentType(resp);
		String sessionId = req.getHeader(ContextKeys.SESSION_ID);
		SessionManager sessionManager = (SessionManager) getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKeys.USER_MANAGER);
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

		Session session = sessionManager.getSession(sessionId);
		try {
			User user = userManager.getUserById(session.getUserId());
			resp.getWriter().write(objectMapper.writeValueAsString(user));
		} catch (InterruptedException | SQLException e) {
			log.error("Error occurred during retrieval of private user data.", e);
		}
	}
}
