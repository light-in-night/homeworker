package org.freeuni.homeworker.server.controller.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author : Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter extends HttpFilter {

    private static final String ADMIN_PASSWORD = "abc";
    /**
     *
     * Handles all /admin/* url calls.
     * admin request must have a <b>HEADER</b> named "adminPassword"
     * that has a valid password.
     * No session is required.
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

        if (request.getHeader("adminPassword") != null &&
                request.getHeader("adminPassword").equals(ADMIN_PASSWORD)) {
                chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/illegalRequest").forward(request, response);
        }
    }

    @Override
    public void destroy() { /* NOT USED */ }

}
