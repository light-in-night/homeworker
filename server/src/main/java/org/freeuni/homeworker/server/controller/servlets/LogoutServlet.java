package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

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
    /**
     * Marks SessionId as logged out.
     * If it is already logged out, this does nothing.
     *
     * Reads :
     *
     * Returns :
     * {
     *     STATUS : OK | ERROR,
     *     ERROR_MESSAGE
     * }
     *
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        SessionManager sessionManager =(SessionManager)request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        String jsonFromRequest = ServletUtils.readFromRequest(request);
        JsonNode requestRoot = objectMapper.readTree(jsonFromRequest);
        ObjectNode responseRoot =  objectMapper.createObjectNode();

        try {
            String sessionID = request.getHeader("sessionId");
            Session session = sessionManager.getSession(sessionID);
            sessionManager.logout(sessionID, session.getUserId());
            JacksonUtils.addStatusOk(responseRoot);
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseRoot, e.getMessage());
        }
        response.getWriter().write(responseRoot.toString());
    }
}
