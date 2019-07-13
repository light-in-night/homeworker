package org.freeuni.homeworker.server.controller.servlets;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.objects.user.UserFactory;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
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
            List<User> users = userManager.getUsers().stream()
                    .filter(user -> request.getParameter("id") == null
                            || Long.parseLong(request.getParameter("id")) == user.getId())
                    .filter(user -> request.getParameter("firstName") == null || request.getParameter("firstName").equals(user.getFirstName()))
                    .filter(user -> request.getParameter("lastName") == null || request.getParameter("lastName").equals(user.getLastName()))
                    .filter(user -> request.getParameter("gender") == null || request.getParameter("gender").equals(user.getGender()))
                    .filter(user -> request.getParameter("email") == null || request.getParameter("email").equals(user.getEmail()))
                    .filter(user -> request.getParameter("password") == null || request.getParameter("password").equals(user.getPassword()))
                    .filter(user -> request.getParameter("k0") == null || (user.getKarma() >= Long.parseLong(request.getParameter("k0"))))
                    .filter(user -> request.getParameter("k1") == null || (user.getKarma() < Long.parseLong(request.getParameter("k1"))))
                    .collect(Collectors.toList());
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


}
