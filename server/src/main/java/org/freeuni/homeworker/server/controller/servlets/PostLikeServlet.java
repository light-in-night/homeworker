package org.freeuni.homeworker.server.controller.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.response.Response;
import org.freeuni.homeworker.server.model.objects.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/postLikeServlet")
public class PostLikeServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        String jSonObject = getJSonStringFromRequest(request);
        executeRequest(request, response, jSonObject);
    }

    /**
     * Executes Given Request
     *
     * @param request
     * @param response
     * @param jSonObject JSon String
     * @throws IOException
     */
    private void executeRequest(HttpServletRequest request, HttpServletResponse response, String jSonObject) {
        Response resp = new Response();
        try {
            PostLike postLikeObject = new ObjectMapper().readValue(jSonObject, PostLike.class);
            PostLikeManager likeModuleManager = (PostLikeManager) request.getServletContext().getAttribute(ContextKeys.POST_LIKE_MANAGER);
            if(postLikeObject.isLiked()){
                if(likeModuleManager.like(postLikeObject)) {
                    resp.setMessage("Request Executed Without Fail");
                    resp.setStatus(ResponseStatus.OK.name());
                } else{
                    resp.setMessage("Error During Adding Post Like");
                    resp.setStatus(ResponseStatus.ERROR.name());
                }
            } else {
                if(likeModuleManager.unLike(postLikeObject)){
                    resp.setStatus(ResponseStatus.OK.name());
                    resp.setMessage("Request Executed Without Fail");
                }else{
                    resp.setStatus(ResponseStatus.ERROR.name());
                    resp.setMessage("Error During Post Dislike");
                }
            }
            ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
            response.getWriter().write(objectMapper.writeValueAsString(resp));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error Occurred, IO Exception, Caused By Either Database Connection, Or Bad JSON");
        }
    }

    /**
     * Reads JSon String From Request
     * @param request Servlet Request
     * @return JSon String
     */
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
