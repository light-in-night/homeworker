package org.freeuni.homeworker.server.controller.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.objects.user.UserFactory;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Tornike Kechakmadze, Tornike Onoprishvili,  (add here)
 * Tested via : SoapUI (EVERY METHOD)
 */
@WebServlet(name = "UserAccessServlet", urlPatterns = {"/admin/users"})
public class UserAccessServlet extends HttpServlet {

    /**
     * Returns and filters all users.
     * Used by admins only.
     *
     * Reads GET parameters:
     * /users
     * ?    id : 123, (OPTIONAL)
     *  &   firstName : "Jon"        (OPTIONAL)
     *  &   lastName : "Snow"        (OPTIONAL)
     *  &   gender : "male"          (OPTIONAL)
     *  &   email : "asdad"          (OPTIONAL)
     *  &   password : "asdasd"      (OPTIONAL)
     *  &   k0 :  0                 (OPTIONAL)  (karma lower bound)
     *  &   k1 : 122                (OPTIONAL)  (karma higher bound)
     *
     * OPTIONAL PARAMS :
     * if params are specified, the output list of users
     * will be filtered by given params.
     * Else, all users are returned.
     *
     * Writes:
     * {
     *     STATUS : OK |
     *     ERROR_MESSAGE : ""
     *     users : [
     *     {
     *          id : 123,
     *          firstName : "Jon"
     *          lastName : "Snow"
     *          gender : "male"
     *          email : "asdad"
     *          password : "asdasd"
     *          karma :  99
     *     },
     *     {
     *         ...
     *     },
     *      ...
     *     ]
     * }
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);
        UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        ObjectMapper mapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

        ObjectNode resultNode = mapper.createObjectNode();
        try {

            User filterUser = getUserFilterFromRequest(request);

            List<User> users = userManager.getUsers(filterUser);

            ArrayNode userArray = mapper.createArrayNode();
            for(User user : users) {
                userArray.add(UserFactory.toObjectNode(user, mapper.createObjectNode()));
            }
            resultNode.set("users", userArray);
            JacksonUtils.addStatusOk(resultNode);
        } catch (InterruptedException | SQLException e) {
            JacksonUtils.addStatusError(resultNode, e.toString());
            e.printStackTrace();
        }
        response.getWriter().write(resultNode.toString());
    }

    /**
     * Generate Filter Object Given From URL
     * @param request
     * @return
     */
    private User getUserFilterFromRequest(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        long karma = Long.parseLong(request.getParameter("karma"));
        User user = new User(id, firstName, lastName, gender, email, password);
        user.setKarma(karma);
        return user;
    }


}
