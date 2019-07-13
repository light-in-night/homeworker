package org.freeuni.homeworker.server.controller.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author : Tornike Kechakhmadze, Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebFilter(urlPatterns = { "/hasSession/isLoggedIn/*"})
public class LoginFilter extends HttpFilter {

	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    /**
	 * Handles all /hasSession/isLoggedIn/* url calls.
	 * Every json request in this url pattern
	 * must have header called sessionId
     * with a value of string.
	 *
	 * @see org.freeuni.homeworker.server.controller.servlets.SessionCreationServlet for creating sessions.
     */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Login Filter");
		SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		//ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		//String jsonString = ServletUtils.readFromRequest(request);
		if (request.getHeader("sessionId") != null
				&& sessionManager.isUserLoggedIn(request.getHeader("sessionId"))) {
				chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/illegalRequest").forward(request, response);
		}
	}

	@Override
	public void destroy() { /* NOT USED */ }
}
