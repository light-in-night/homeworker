package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.user.User;
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
 * Author : Tornike kechakhmadze, Tornike onoprishvili
 * Tested via : SoapUI
 */
@SuppressWarnings("RedundantThrows")
@WebServlet(urlPatterns = "/hasSession/login")
public class LoginServlet extends HttpServlet {

	private final Logger log = LoggerFactory.getLogger(LoginServlet.class);

	/**
	 * Used to check login status.
	 *
	 * Reads :
	 *
	 * Returns :
	 * {
	 *     STATUS : OK | ERROR,
	 *     ERROR_MESSAGE : ""
	 *     loggedIn : true | false
	 * }
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ServletUtils.setCORSHeaders(response);
		ServletUtils.setJSONContentType(response);

		SessionManager sessionManager =(SessionManager)request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		//UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
		ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

		ObjectNode responseRoot =  objectMapper.createObjectNode();

		try {
			//String jsonFromRequest = ServletUtils.readFromRequest(request);
			//JsonNode requestRoot = objectMapper.readTree(jsonFromRequest);
			String sessionId = request.getHeader(ContextKeys.SESSION_ID);
			responseRoot.put("loggedIn", sessionManager.getSession(sessionId).isLoggedIn());
			JacksonUtils.addStatusOk(responseRoot);
		} catch (Exception e) {
			e.printStackTrace();
			JacksonUtils.addStatusError(responseRoot, e.getMessage());
			log.error("Error occurred during logging in.", e);
		}
		response.getWriter().write(responseRoot.toString());
	}

	/**
	 *
	 * 	This method is used to authenticate a session.
	 * 	<b>To use this servlet, user must add a single header</b>
	 * 	That header is "sessionId"
	 * 	It must contain a string that represents a session id of the given user.
	 * 	the session Id is generated in SessionServlet
	 *
	 *	Reads :
	 *	{
	 *	    email : "myemail@asdasd",
	 *	    password : "mypass"
	 *	} (ALL FIELDS REQUIRED)
	 *
	 * 	Writes :
	 * 	{
	 * 	    STATUS : OK | ERROR		(OK means that you are now logged in)
	 * 	    ERROR_MESSAGE : ""
	 * 	}
	 *
	 * @see SessionServlet SessionServlet.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(response);
		ServletUtils.setJSONContentType(response);

		ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
		SessionManager sessionManager =(SessionManager)request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

		String jsonFromRequest = ServletUtils.readFromRequest(request);
		JsonNode requestRoot = objectMapper.readTree(jsonFromRequest);
		ObjectNode responseRoot =  objectMapper.createObjectNode();

		try {
			String email = requestRoot.get("email").asText();
			String password = requestRoot.get("password").asText();
			String sessionID = request.getHeader(ContextKeys.SESSION_ID);

			User user = userManager.getUserByEmail(email);
			if (user == null) {
				throw new Exception("User is not logged in.");
			}
			if(user.getPassword().equals(password)) {
				sessionManager.login(sessionID, user.getId());
				JacksonUtils.addStatusOk(responseRoot);
			} else {
				sessionManager.registerUnsuccessfulLogin(sessionID);
				JacksonUtils.addStatusError(responseRoot, "Detected incorrect credentials.");
			}
		} catch (Exception e) {
			JacksonUtils.addStatusError(responseRoot, e.getMessage());
			log.error("Error occurred.", e);
		}
		response.getWriter().write(responseRoot.toString());
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		resp.setStatus(200);
	}

}
