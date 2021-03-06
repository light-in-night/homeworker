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

/**
 * Author : Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "CountPostsByCategoriesServlet", urlPatterns = "/categories/stats")
public class CountPostsByCategoriesServlet extends HttpServlet {

    /**
     *
     * Returns categories and post counts.
     *
     * Reads :
     *
     * Does not read.
     *
     * Returns :
     * {
     *     STATUS : "OK"
     *     ERROR_MESSAGE : ""
     *     categories :
     *     [{
     *         "id" : 123,      (id of category)
     *         "name" : "jobs"  (name of category)
     *         "description" : "where people work" (description of category)
     *         "postCount" : 3123 (number of posts in that category)
     *     },
     *     {
     *       "categoryId" : 124,
     *       ...
     *     }
     *     ...
     *     ]
     * }
     *
     * Author : Tornike Onoprishvili
     * Tested via : SOAPUI (EVERY METHOD)
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostCategoryManager postCategoryManager = (PostCategoryManager) request.getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        CategoryManager categoryManager = (CategoryManager) request.getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            List<Category> allCategories = categoryManager.getAllCategories();
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for(Category category : allCategories) {
                List<Post> postsByCategory = postCategoryManager.getPostsInCategory(category.getId());
                ObjectNode responseJsonObject = objectMapper.createObjectNode();
                responseJsonObject.put("id", category.getId());
                responseJsonObject.put("categoryName", category.getName());
                responseJsonObject.put("description", category.getDescription());
                responseJsonObject.put("postCount", postsByCategory.size());
                arrayNode.add(responseJsonObject);
            }
            objectNode.set("categories", arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }

        response.getWriter().write(objectNode.toString());
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(resp);
        resp.setStatus(200);
    }
}
