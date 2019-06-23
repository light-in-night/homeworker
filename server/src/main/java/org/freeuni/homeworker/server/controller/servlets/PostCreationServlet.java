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

@SuppressWarnings("RedundantThrows")
@WebServlet(name = "PostMakingServlet", urlPatterns = {"/secure/createPost"})
public class PostCreationServlet extends HttpServlet {

    /**
     * This servlet creates posts.
     * to use it, you must send JSON in this format:
     *  {
     *      "userId" : 123456,
     *      "content" : "hello world",
     *      "category" : "lifestyle"
     *  }
     *
     *  that will create a new post that has category 'lifestyle"
     *  has an author with id of 123456
     *  and has a content of 'hello world'
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);

        String jsonStr = ServletUtils.readFromRequest(request);
        Post post = objectMapper.readValue(jsonStr,Post.class);
        postManager.add(post);
    }
}
