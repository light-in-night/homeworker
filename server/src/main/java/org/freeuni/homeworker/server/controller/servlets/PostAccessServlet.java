package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PostAccessServlet", urlPatterns = {"/getpost"})
public class PostAccessServlet extends HttpServlet {

    /**
     * Returns every post from Database.
     * requires no arguments to be passed in.
     * The returned JSON will have a format
     * for example like this:
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
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        PostManager postManager = (PostManager) request.getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);

        List<Post> postList = postManager.getAllPosts();

        ServletOutputStream out = response.getOutputStream();

        String jsonOutput = objectMapper.writeValueAsString(postList);

        out.print(jsonOutput);
    }
}
