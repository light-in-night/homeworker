package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postEdit.PostEditManager;
import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PostEditServlet")
public class PostEditServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(PostEditServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // JSON string is given through request by name json
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);
        String jSonObject = ServletUtils.readFromRequest(request);
        //mapper to treat JSON
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        //turns json into java object
        try {
            PostEditObject obj = objectMapper.readValue(jSonObject, PostEditObject.class);
            PostEditManager postManager = (PostEditManager) request.getServletContext().getAttribute(ContextKeys.POST_EDIT_MANAGER);
            postManager.editPost(obj);
        } catch (IOException e) {
            log.error("Error Occurred, IO Exception, Caused By Either Database Connection, Or Bad JSON.", e);
        }
    }


}
