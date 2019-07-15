package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.category.CategoryFactory;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryAccessServlet", urlPatterns = {"/categories"})
public class CategoryAccessServlet extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(CategoryAccessServlet.class);

    /**
     * Returns categories in database. Can be filtered.
     * Reads :
     * /categories              (ALL PARAMS ARE OPTIONAL)
     * ? id=123                  (OPTIONAL)
     * & name = "category name" (FULL MATCH) (OPTIONAL)
     *
     * OPTIONAL PARAMS:
     * IF ANY OF THE PARAMS ARE NOT GIVEN,
     * RETURNED CATEGORIES WILL NOT BE FILTERED BY THEM.
     * IF NO FILTERS ARE GIVEN, ALL OBJECTS ARE RETURNED
     *
     * Returns :
     * {
     *     STATUS : "OK"
     *     ERROR_MESSAGE : ""
     *     categories : [
     *          {
     *                  id : 1234,
     *                  name : 'jobs',
     *                  description : 'posts about jobs'
     *          },
     *          {
     *              ...
     *          },
     *          ...
     *     ]
     * }
     *
     * Author : Tornike Onoprishvili
     * Tested via : SOAPUI (All methods)
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CategoryManager categoryManager = (CategoryManager) request.getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER);
        PostCategoryManager postCategoryManager = (PostCategoryManager) request.getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            List<Category> categories = categoryManager.getAllCategories();
            for(Category category : categories){
                long count = postCategoryManager.getCountNumberOfPostsByCategory(category.getId());
                category.setCount(count);
            }
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for(Category category : categories) {
                arrayNode.add(CategoryFactory.toObjectNode(category, objectMapper.createObjectNode()));
            }
            objectNode.set("categories",arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            log.error("Error occurred during getting categories.", e);
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
