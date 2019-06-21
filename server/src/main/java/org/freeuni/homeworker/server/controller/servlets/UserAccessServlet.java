package org.freeuni.homeworker.server.controller.servlets;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.freeuni.homeworker.server.model.objects.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserAccessServlet", urlPatterns = {"/getusers"})
public class UserAccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        Object obj = request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        UserManager userManager = (UserManager) obj;

        List<User> users = userManager.getUsers();

        response.setContentType("application/json;charset=UTF-8");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String output = gson.toJson(users);
        response.getWriter().write(output);
    }
}
