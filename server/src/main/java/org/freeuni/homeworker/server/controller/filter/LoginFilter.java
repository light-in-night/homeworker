package org.freeuni.homeworker.server.controller.filter;

import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.controller.session.Session;
import org.freeuni.homeworker.server.controller.session.SessionManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("RedundantThrows")
public class LoginFilter extends HttpFilter {


	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String sessionId = request.getHeader(ContextKeys.SESSION_ID);
		SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
	}

	@Override
	public void destroy() {

	}
}
