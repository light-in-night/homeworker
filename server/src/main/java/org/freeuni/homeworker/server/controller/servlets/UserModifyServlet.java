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
import java.sql.SQLException;

@WebServlet(name = "UserModifyServlet", urlPatterns = "/hasSession/isLoggedIn/users")
public class UserModifyServlet extends HttpServlet {

    /**
     * Updates existing user account.
     * Reads :
     * {
     *     id : 123 (user id)           (REQUIRED)
     *     firstName : "new name",     (OPTIONAL)
     *     lastName : "last name",      (OPTIONAL)
     *     gender : "new gender"        (OPTIONAL)
     *     password : "new password"    (OPTIONAL)
     * }
     *
     * Writes :
     *
     * {
     *      STATUS : OK | ERROR
     *      ERROR_MESSAGE : '' | 'some error message'
     * }
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
        String jsonRequest = ServletUtils.readFromRequest(request);
        JsonNode requestNode = objectMapper.readTree(jsonRequest);
        ObjectNode returnObjectNode = objectMapper.createObjectNode();

        try {
            Session session = sessionManager.getSession(request.getHeader("sessionId"));
            if(session.getUserId() == requestNode.get("id").asLong()) {
                User oldUser = userManager.getUserById(session.getUserId());
                if(requestNode.has("firstName")) {
                    oldUser.setFirstName(requestNode.get("firstName").asText());
                }

                if(requestNode.has("lastName")) {
                    oldUser.setLastName(requestNode.get("lastName").asText());
                }

                if(requestNode.has("gender")) {
                    oldUser.setGender(requestNode.get("gender").asText());
                }

                if(requestNode.has("password")) {
                    oldUser.setPassword(requestNode.get("password").asText());
                }

                userManager.updateUser(oldUser);
                JacksonUtils.addStatusOk(returnObjectNode);
            }
        } catch (Exception e) {
            JacksonUtils.addStatusError(returnObjectNode, e.toString());
        }

        response.getWriter().write(returnObjectNode.toString());
    }
}
