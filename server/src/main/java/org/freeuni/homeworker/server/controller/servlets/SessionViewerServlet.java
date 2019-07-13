package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * Author : Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "SessionViewerServlet",urlPatterns = "/admin/sessions")
public class SessionViewerServlet extends HttpServlet {
    /**
     * Returns list of all active sessions.
     *
     * Reads :
     *
     * Returns :
     * {
     *    "STATUS": "OK" | ERROR
     *    ERROR_MESSAGE : ""
     *    "sessions": [   {
     *       "sessionId": "test",
     *       "userId": null,
     *       "loggedIn": false
     *    }],
     * }
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(resp);
        ServletUtils.setJSONContentType(resp);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        SessionManager sessionManager = (SessionManager) getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        ObjectNode responseJsonNode = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for(Map.Entry<String, Session> e : sessionManager.getAllSessions()) {
            String sessionId = e.getKey();
            Session session = e.getValue();
            ObjectNode itemNode = objectMapper.createObjectNode();
            itemNode.put(ContextKeys.SESSION_ID, sessionId);
            itemNode.put("userId",session.getUserId());
            itemNode.put("loggedIn",session.isLoggedIn());
            arrayNode.add(itemNode);
        }
        responseJsonNode.set("sessions", arrayNode);
        JacksonUtils.addStatusOk(responseJsonNode);
        resp.getWriter().write(responseJsonNode.toString());
    }
}
