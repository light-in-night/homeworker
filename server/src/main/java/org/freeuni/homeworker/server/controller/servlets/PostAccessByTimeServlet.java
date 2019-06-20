package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "PostAccessByTimeServlet", urlPatterns = "/getpost/byTime")
public class PostAccessByTimeServlet extends HttpServlet {
    /**
     * Returns JSON list of posts between given two times.
     * the times are in format yyyy-[m]m-[d]d hh:mm:ss[.f...]. format. for example '2019-1-5 23:12:31'.
     * you must provide two parameters : startTime, endTime, both having above said formats.
     * this will return JSON of list of posts :
     * example : {
     *      [{
     *          "id" : 1234,
     *          "userId" : 123,
     *          "content" : "hello world",
     *          "rating" : 31,
     *          "creationtimestamp" : 31234124 (just google unix epoch)
     *          "category" : "jobs" (this will be removed in future)
     *      },
     *      {
     *          "id" : 13312,
     *          "userId" : 123,
     *          ...
     *      }
     *      ...
     *      ]
     *  }
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilities.setJSONContentType(response);
        Utilities.setCORSHeaders(response);

        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

        Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
        Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
        List<Post> posts = postManager.getPostsBetweenTimes(startTime, endTime);
        response.getWriter().write(objectMapper.writeValueAsString(posts));
    }
}
