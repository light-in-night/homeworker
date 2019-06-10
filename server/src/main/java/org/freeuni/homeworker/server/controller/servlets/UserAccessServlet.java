package org.freeuni.homeworker.server.controller.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.freeuni.homeworker.server.model.objects.user.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserAccessServlet", urlPatterns = {"/getusers","/getUsers"})
public class UserAccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        Object obj = request.getServletContext().getAttribute(ContextKeys.USER_MANAGER);
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(UserManager.class.getClassLoader());
        
        // THIS LINE.
        
        UserManagerSQL userManager = (UserManagerSQL) obj;

        //TODO: HELP ! CANNOT CAST A CLASS TO ITS INTERFACE. STUPID JAVA. 

        List<User> userList = new ArrayList<>();
        userList.add(new User(0,"Givi","Racxa","pansexual","givi@gmail","lels"));
        userList.add(new User(0,"Marco","eewq","sapiosexual","givi@gmail","dad"));

        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream out = response.getOutputStream();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String output = gson.toJson(userList);
        out.print(output);
    }
}
