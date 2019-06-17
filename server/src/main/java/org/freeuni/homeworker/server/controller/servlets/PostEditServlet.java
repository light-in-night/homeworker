package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postEdit.PostEditManager;
import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PostEditServlet")
public class PostEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // JSON string is given through request by name json
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        String jSonObject = getJSonStringFromRequest(request);
        //mapper to treat JSON
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        //turns json into java object
        try {
            PostEditObject obj = objectMapper.readValue(jSonObject, PostEditObject.class);
            PostEditManager postManager = (PostEditManager) request.getServletContext().getAttribute(ContextKeys.POST_EDIT_MANAGER);
            postManager.editPost(obj);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Occurred, IO Exception, Caused By Either Database Connection, Or Bad JSON");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private String getJSonStringFromRequest(HttpServletRequest request) {
        StringBuffer jSonString = new StringBuffer();
        try {
            BufferedReader reader = request.getReader();
            while (true){
                String line = reader.readLine();
                if(line == null ){ break; }
                jSonString.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSonString.toString();
    }

}
