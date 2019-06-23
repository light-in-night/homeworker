package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostAccessByCategoryServlet", urlPatterns = "/getpost/bycategory")
public class PostAccessByCategoryServlet extends HttpServlet {

    /**
     * Returns list of posts of given category.
     * Expects a Parameter named categoryId that is a number.
     * returns a JSON of given format :
     * example : {
     *      STATUS : "OK",
     *      ERROR_MESSAGE : "",
     *
     *      posts : [{
     *          "id" : 1234,
     *          "userId" : 123,
     *          "content" : "hello world",
     *          "creationtimestamp" : 31234124 (just google unix epoch)
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        PostCategoryManager postCategoryManager = (PostCategoryManager) getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            long categoryId = Long.parseLong(request.getParameter("categoryId"));
            List<Post> postList = postCategoryManager.getPostsInCategory(categoryId);
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for(Post post : postList) {
                arrayNode.add(objectMapper.writeValueAsString(post));
            }
            objectNode.set("posts", arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }

        response.getWriter().write(objectNode.toString());
    }
}
