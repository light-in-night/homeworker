package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PostAccessByCategoryServlet", urlPatterns = "/getpost/bycategory")
public class PostAccessByCategoryServlet extends HttpServlet {

    /**
     * Returns list of posts of given category.
     * Expects a Parameter named categoryId that is a number.
     * returns a JSON of given format :
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
     **/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilities.setCORSHeaders(response);
        Utilities.setJSONContentType(response);

        PostCategoryManager postCategoryManager = (PostCategoryManager) getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        long categoryId = Long.parseLong((String) request.getParameter("categoryId"));
        List<Post> posts = postCategoryManager.getPostsInCategory(categoryId);
        response.getWriter().write(objectMapper.writeValueAsString(posts));
    }
}
