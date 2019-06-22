package org.freeuni.homeworker.server.controller.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.response.Response;
import org.freeuni.homeworker.server.model.objects.response.ResponseStatus;
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


@WebServlet("/postLikeServlet")
public class PostLikeServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);
        String jSonObject = ServletUtils.readFromRequest(request);
        executeRequest(request, response, jSonObject);
    }

    /**
     * Executes Given Request
     *
     * @param request @code HttpServletRequest
     * @param response @code HttpServletResponse
     * @param jSonObject JSon @code String
     * @throws @code IOException.class
     */
    private void executeRequest(HttpServletRequest request, HttpServletResponse response, String jSonObject) {
        Response resp = new Response();
        try {
            ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
            PostLike postLikeObject = objectMapper.readValue(jSonObject, PostLike.class);
            if(invalidJSon(postLikeObject)){
                resp.setMessage("Invalid JSon");
                resp.setStatus(ResponseStatus.ERROR.name());
                response.getWriter().write(objectMapper.writeValueAsString(resp));
            } else {
                PostLikeManager likeModuleManager = (PostLikeManager) request.getServletContext().getAttribute(ContextKeys.POST_LIKE_MANAGER);
                if (postLikeObject.isLiked()) {
                    if (likeModuleManager.like(postLikeObject)) {
                        resp.setMessage("Request Executed Without Fail");
                        resp.setStatus(ResponseStatus.OK.name());
                    } else {
                        resp.setMessage("Error During Adding Post Like");
                        resp.setStatus(ResponseStatus.ERROR.name());
                    }
                } else {
                    if (likeModuleManager.unLike(postLikeObject)) {
                        resp.setStatus(ResponseStatus.OK.name());
                        resp.setMessage("Request Executed Without Fail");
                    } else {
                        resp.setStatus(ResponseStatus.ERROR.name());
                        resp.setMessage("Error During Post Dislike");
                    }
                }
                response.getWriter().write(objectMapper.writeValueAsString(resp));

            }
        } catch (IOException e) {
            log.error("Error Occurred, IO Exception, Caused By Either Database Connection, Or Bad JSON.", e);
        }
    }

    private boolean invalidJSon(PostLike postLikeObject) {
        return postLikeObject == null || postLikeObject.containsNullFields();
    }
}
