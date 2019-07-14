package org.freeuni.homeworker.server.controller.filter;

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
 * Author : Guram Tkesheladze, Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebFilter(urlPatterns = "/hasSession/*")
public class SessionFilter  extends HttpFilter {



    private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);

    /**
     * Handles all /hasSession/* url calls.
     * Every request in this url pattern
     * must have header called sessionId
     * with a value of string.
     *
     * @see org.freeuni.homeworker.server.controller.servlets.SessionCreationServlet for creating sessions.
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        if (request.getHeader(ContextKeys.SESSION_ID)!= null && sessionManager.hasSession(request.getHeader(ContextKeys.SESSION_ID))) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(ContextKeys.ILLEGAL_REQUEST).forward(request, response);
        }
    }

    @Override
    public void destroy() { /* NOT USED */ }
}
