package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategoryFactory;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Tornike onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(urlPatterns = "/hasSession/isLoggedIn/posts")
public class PostCreateModifyServlet extends HttpServlet {

    /**
     * Creates a new post. Adds categories to it.
     *
     * Reads:
     *
     *  {
     *      "post" : {
     *       "contents" : "jon snow's post! winter is cumming!"
     *      },
     *      "categories" : [
     *          1,
     *          2
     *      ]
     * }
     *
     *  OPTIONAL PARAMS :
     *  if categories array is specified,
     *  this servlet will add entries to postCategory table.
     *  This will register the new post in specified categories.
     *
     *  Returns :
     *  {
     *      STATUS : "OK" | "ERROR"
     *      ERROR_MESSAGE : ""
     *      id : 123 (id of created post)
     *  }
     *
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        PostCategoryManager postCategoryManager = (PostCategoryManager) request.getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        String jsonStr = ServletUtils.readFromRequest(request);
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        ObjectNode returnObjectNode = objectMapper.createObjectNode();
        Post post = objectMapper.readValue(rootNode.get("post").toString(),Post.class);
        Session userSession = sessionManager.getSession(request.getHeader("sessionId"));

        try {
            post.setUserId(userSession.getUserId());
            long postId = postManager.add(post);
            returnObjectNode.put("id", postId);
            if(rootNode.has("categories")) {
                for (int i = 0; i < rootNode.get("categories").size(); i++) {
                    long categoryId = rootNode.get("categories").get(i).asLong();
                    postCategoryManager.add(PostCategoryFactory.fromPostAndCategoryId(0, postId, categoryId));
                }
            }
            JacksonUtils.addStatusOk(returnObjectNode);
        } catch (Exception e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(returnObjectNode, e.toString());
        }
        response.getWriter().write(returnObjectNode.toString());
    }


    /**
     *
     * TODO : DOES NOT WORK because PostManager does not work.
     *
     * Updates existing post's text.
     *
     * Reads:
     * {
     *     id : 123 (post id),
     *     contents : "new post text",
     * }
     *
     * Returns:
     * {
     *     STATUS : "OK" | "ERROR"
     *     ERROR_MESSAGE : ""
     * }
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        PostManager postManager = (PostManager) request.getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        String jsonStr = ServletUtils.readFromRequest(request);
        ObjectNode returnObjectNode = objectMapper.createObjectNode();
        JsonNode rootNode = objectMapper.readTree(jsonStr);

        try {
            Post newPost = objectMapper.readValue(jsonStr, Post.class);
            Session userSession = sessionManager.getSession(request.getHeader("sessionId"));
            newPost.setUserId(userSession.getUserId());
            newPost.setId(rootNode.get("id").asLong());
            long userId = userSession.getUserId();
            if(userId == postManager.getById(newPost.getId()).getUserId()) { //if user owns the post.
                postManager.updatePostContents(newPost);
                JacksonUtils.addStatusOk(returnObjectNode);
            } else {
                JacksonUtils.addStatusError(returnObjectNode, "That is not your post.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(returnObjectNode, e.toString());
        }


        response.getWriter().write(returnObjectNode.toString());
    }
}
