package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @param request @code HttpServletRequest
     * @param response @code HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        String jsonString = ServletUtils.readFromRequest(request);
        Category category = objectMapper.readValue(jsonString, Category.class);
        categoryManager.add(category);
    }
}
