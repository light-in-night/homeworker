package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
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

@WebServlet(name = "CountPostsByCategoriesServlet", urlPatterns = "/getpost/countbycategory")
public class CountPostsByCategoriesServlet extends HttpServlet {

    /**
     *
     * Does not take in any arguments.
     * Returns JSON in this format:
     * {
     *     STATUS : "OK"
     *     ERROR_MESSAGE : ""
     *     data :
     *     [{
     *         "categoryId" : 123, (same as categoryId)
     *         "categoryName" : "jobs"
     *         "description" : "where people work"
     *         "postCount" : 3123
     *     },
     *     {
     *       "categoryId" : 124,
     *       ...
     *     }
     *     ...
     *     ]
     * }
     *
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostCategoryManager postCategoryManager = (PostCategoryManager) getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            List<Category> allCategories = categoryManager.getAllCategories();
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for(Category category : allCategories) {
                List<Post> postsByCategory = null;
                    postsByCategory = postCategoryManager.getPostsInCategory(category.getId());
                ObjectNode responseJsonObject = objectMapper.createObjectNode();
                responseJsonObject.put("categoryId", category.getId());
                responseJsonObject.put("categoryName", category.getName());
                responseJsonObject.put("description", category.getDescription());
                responseJsonObject.put("postCount", postsByCategory.size());
                arrayNode.add(responseJsonObject);
            }
            objectNode.set("data", arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }

        response.getWriter().write(objectNode.toString());
    }
}
