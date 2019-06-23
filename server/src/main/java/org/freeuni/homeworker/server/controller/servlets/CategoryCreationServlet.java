package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CategoryCreationServlet", urlPatterns = "/createcategory")
public class CategoryCreationServlet extends HttpServlet {
    /**
     * Creates a new Category.
     * Reads as input a JSON String in following format:
     * {
     *     "name" : 'jobs',
     *     "description" : 'where people work'
     * }
     *
     * returns json like
     * {
     *     STATUS : "OK"
     *     ERROR_MESSAGE : ""
     *     categoryId : 123
     * }
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        String jsonString = ServletUtils.readFromRequest(request);
        Category category = objectMapper.readValue(jsonString, Category.class);
        try {
            long categoryId = categoryManager.add(category);
            objectNode.put("categoryId", categoryId);
            JacksonUtils.addStatusOk(objectNode);
        } catch (InterruptedException | SQLException e) {
            JacksonUtils.addStatusError(objectNode, e.toString());
            e.printStackTrace();
        }
        response.getWriter().write(objectMapper.writeValueAsString(objectNode));
    }
}
