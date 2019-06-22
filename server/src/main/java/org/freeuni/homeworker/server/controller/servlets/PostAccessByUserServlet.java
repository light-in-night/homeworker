package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PostAccessByUserServlet",urlPatterns = "/getpost/byuser")
public class PostAccessByUserServlet extends HttpServlet {
    /**
     *  Expects an single integer argument with a name
     *  userId.
     *  As an example it can be passed in via this url link :
     *  http//:localhost:8089/war_exploded/getPosts/byUser/?userId=123
     *
     *   the returned JSON will contain all the posts by user with id 123
     *  for example
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

        String userIdString = request.getParameter("userId");
        List<Post> posts = postManager.getPostsByUser(Long.parseLong(userIdString));
        response.getWriter().write(objectMapper.writeValueAsString(posts));
    }
}
