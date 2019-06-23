package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryAccessServlet", urlPatterns = "/getcategory")
public class CategoryAccessServlet extends HttpServlet {
    /**
     * Returns every category in the database.
     * Returns JSON String of all categories in this format
     *
     * {
     *      STATUS : "OK"
     *      ERROR_MESSAGE : ""
     *     categories : [{
     *              id : 1234,
     *              name : 'jobs',
     *              description : 'posts about jobs'
     *          },
     *          ...
     *     ]
     * }
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            List<Category> categories = categoryManager.getAllCategories();

            ArrayNode arrayNode = objectMapper.createArrayNode();
            for(Category post : categories) {
                arrayNode.add(objectMapper.writeValueAsString(post));
            }
            objectNode.set("categories",arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }
        response.getWriter().write(objectNode.toString());
    }
}
