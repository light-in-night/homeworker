package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
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

/**
 * Author : Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "CategoryCreateModifyServlet", urlPatterns = "/admin/categories")
public class CategoryCreateModifyServlet extends HttpServlet {
    /**
     * Creates a new Category.
     *
     * Reads:
     * {
     *     "name" : "jobs",
     *     "description" : "where people work"
     * }
     *
     * Returns:
     * {
     *     STATUS : "OK" | "ERROR"
     *     ERROR_MESSAGE : "" | "Some error message"
     *     id : 123         (id of newly created category)
     * }
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CategoryManager categoryManager = (CategoryManager) request.getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectNode returnNode = objectMapper.createObjectNode();

        try {
            String jsonString = ServletUtils.readFromRequest(request);
            Category category = objectMapper.readValue(jsonString, Category.class);
            JsonNode requestNode = objectMapper.readTree(jsonString);
            long categoryId = categoryManager.add(category);
            returnNode.put("id", categoryId);
            JacksonUtils.addStatusOk(returnNode);
        } catch (Exception e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(returnNode, e.toString());
        }
        response.getWriter().write(returnNode.toString());
    }

    /**
     * Modifies a category
     * Reads
     * {
     *     id : 123     (category id)
     *     name : "newName"     (category name)
     *     description : "new description"  (category description)
     * }
     *
     * Returns
     * {
     *     STATUS : "OK" | "NOT FOUND" | "ERROR"
     *     ERROR_MESSAGE : "" | "Some error message"
     * }
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJSONContentType(resp);
        ServletUtils.setCORSHeaders(resp);

        CategoryManager categoryManager = (CategoryManager) req.getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) req.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode responseNode = objectMapper.createObjectNode();
        String jsonString = ServletUtils.readFromRequest(req);

        try {
            Category category = objectMapper.readValue(jsonString, Category.class);
            categoryManager.modifyCategory(category);
            JacksonUtils.addStatusOk(responseNode);
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseNode, e.toString());
            e.printStackTrace();
        }
        resp.getWriter().write(responseNode.toString());
    }
}
