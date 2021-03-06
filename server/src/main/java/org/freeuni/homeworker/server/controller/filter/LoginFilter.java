package org.freeuni.homeworker.server.controller.filter;

import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.controller.servlets.SessionServlet;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
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
 * Author : Guram Tkesheladze, incorrect git entries because of file refactor by Tornkike Onoprishvili
 * Tested via : SoapUI
 */
public class LoginFilter extends HttpFilter {

	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    /**
	 * Handles all /hasSession/isLoggedIn/* url calls.
	 * Every json request in this url pattern
	 * must have header called sessionId
     * with a value of string.
	 *
	 * @see SessionServlet for creating sessions.
     */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Login Filter");
		String sessionId = request.getHeader(ContextKeys.SESSION_ID);
		SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
		if (sessionId != null && sessionManager.isUserLoggedIn(sessionId)) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/illegalRequest").forward(request, response);
		}
	}

	@Override
	public void destroy() { /* NOT USED   */ }
}
