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

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        String jsonString = ServletUtils.readFromRequest(request);
        JsonNode requestNode = objectMapper.readTree(jsonString);
        Category category = objectMapper.readValue(jsonString, Category.class);
        try {
            long categoryId = categoryManager.add(category);
            objectNode.put("id", categoryId);
            JacksonUtils.addStatusOk(objectNode);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }
        response.getWriter().write(objectNode.toString());
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
        CategoryManager categoryManager = (CategoryManager) getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();
        String jsonString = ServletUtils.readFromRequest(req);

        try {
            Category category = objectMapper.readValue(jsonString, Category.class);
            categoryManager.modifyCategory(category);
            JacksonUtils.addStatusOk(objectNode);
        } catch (InterruptedException | SQLException e) {
            JacksonUtils.addStatusError(objectNode, e.toString());
            e.printStackTrace();
        }
        resp.getWriter().write(objectMapper.writeValueAsString(objectNode));
    }
}
