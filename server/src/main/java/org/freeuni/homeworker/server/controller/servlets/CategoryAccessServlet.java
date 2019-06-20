package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryAccessServlet", urlPatterns = "/getcategory")
public class CategoryAccessServlet extends HttpServlet {
    /**
     * Returns every category in the database.
     * Returns JSON String of categories in this format
     *
     * {
     *     id : 1234,
     *     name : 'jobs',
     *     description : 'posts about jobs'
     * }
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilities.setCORSHeaders(response);
        Utilities.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);

        List<Category> categories = categoryManager.getAllCategories();
        String text = objectMapper.writeValueAsString(categories);
        response.getWriter().write(text);
    }
}
