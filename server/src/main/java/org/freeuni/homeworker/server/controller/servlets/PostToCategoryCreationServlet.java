package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategoryFactory;
import org.freeuni.homeworker.server.model.objects.postLike.PostLikeFactory;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This servlet is used if we want to manually
 * add category to post relationships.
 *
 * Author : Tornike Onoprishvili
 * Tested via : SoapUI
 *
 */
@WebServlet(name = "PostToCategoryCreationServlet", urlPatterns = "/hasSession/isLoggedIn/posts/addCategory")
public class PostToCategoryCreationServlet extends HttpServlet {
    /**
     *  Replaces categories on post.
     *
     *  Reads:
     *  {
     *      postId : 123,
     *      categories : [
     *          1,2,3 (category ids)
     *      ]
     *  }
     *
     *  Returns :
     *  {
     *      STATUS : OK | ERROR | ILLEGAL
     *      ERROR_MESSAGE : ""
     *  }
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        PostCategoryManager postCategoryManager = (PostCategoryManager) getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode responseNode = objectMapper.createObjectNode();
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        String jsonString = ServletUtils.readFromRequest(request);
        JsonNode requestNode = objectMapper.readTree(jsonString);

        try {
            long postId = requestNode.get("postId").asLong();
            long userId = sessionManager.getSession(request.getHeader("sessionId")).getUserId();
            if (postManager.getById(postId).getUserId() == userId) {    //if belongs to user.
                List<Long> categoryIds = new ArrayList<>();
                for (int i = 0; i < requestNode.get("categories").size(); i++) {
                    categoryIds.add(requestNode.get("categories").get(i).asLong());
                }
                postCategoryManager.removeByPostId(postId);
                for(Long categoryId : categoryIds) {
                    postCategoryManager.add(PostCategoryFactory.createNew(postId,categoryId));
                }
                JacksonUtils.addStatusOk(responseNode);
            } else {
                JacksonUtils.addStatusError(responseNode, "That is not your post.");
            }
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseNode, e.toString());
            e.printStackTrace();
        }
        response.getWriter().write(responseNode.toString());
    }
}
