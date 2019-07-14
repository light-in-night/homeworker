package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
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

/**
 * Author : Tornike Kechakhmadze, Tornike Onoprishvili
 * Tested via :  SoapUI
 */
@WebServlet(name = "UserCreationServlet", urlPatterns = "/users")
public class UserCreationServlet extends HttpServlet {

    /**
     * Creates a new user.
     *
     * Reads :
     * {
     * 	    firstName : Jon,
     * 	    lastName : Snow,
     * 	    gender : male,
     * 	    email : jsnow@stark-mail.wf,
     *      password : iDontKnowAnything
     * }(ALL FIELDS ARE REQUIRED)
     *
     * returns a resulting JSON that
     * shows if user was added successfully or not.
     * the response will have following structure
     *
     *      TODO : (WILL BE ADDED IN FUTURE)
     * {
     *      STATUS : OK | ERROR,
     *      ERROR_MESSAGE : '' | 'ERROR MESSAGE'
     *      **userId** : 123    (will be added in future)
     *                          (id of created user)
     *                          (can be used to automatically log-in new user)
     * }
     *
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletUtils.setCORSHeaders(resp);
        ServletUtils.setJSONContentType(resp);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        ObjectNode resultObjectNode = objectMapper.createObjectNode();

        try {
            User user = objectMapper.readValue(ServletUtils.readFromRequest(req), User.class);
            userManager.addUser(user);
            JacksonUtils.addStatusOk(resultObjectNode);
        }   catch (InterruptedException | SQLException e) {
            JacksonUtils.addStatusError(resultObjectNode, e.toString());
            e.printStackTrace();
        }
        resp.getWriter().write(objectMapper.writeValueAsString(resultObjectNode));
    }


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(resp);
        resp.setStatus(200);
    }
}
