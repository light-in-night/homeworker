package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet(name = "PostToCategoryCreationServlet", urlPatterns = "/addpostcategory")
public class PostToCategoryCreationServlet extends HttpServlet {
    /**
     *  Takes JSON:
     *  {
     *      postId : 123,
     *      categoryId : 231,
     *  }
     *
     *  Returns JSON:
     *  {
     *      STATUS : OK
     *      ERROR_MESSAGE : ""
     *  }
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        PostCategoryManager postCategoryManager = (PostCategoryManager) getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        String jsonString = ServletUtils.readFromRequest(request);
        try {
            PostCategory postCategory = objectMapper.readValue(jsonString, PostCategory.class);
            postCategoryManager.add(postCategory);
            JacksonUtils.addStatusOk(objectNode);
        } catch (InterruptedException | SQLException e) {
            JacksonUtils.addStatusError(objectNode, e.toString());
            e.printStackTrace();
        }
        response.getWriter().write(objectMapper.writeValueAsString(objectNode));
    }
}
