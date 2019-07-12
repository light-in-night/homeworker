package org.freeuni.homeworker.server.controller.servlets;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.postLike.PostLikeFactory;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Author : The Givi, Tornike Onopra
 * Tested via : SoapUI (PARTIAL TESTED)
 */
@WebServlet(name = "PostLikeServlet", urlPatterns = {"/hasSession/isLoggedIn/likeThePost"})
public class PostLikeServlet extends HttpServlet {

    /**
     * TODO : DOES NOT WORK because PostLikeManager uses PostManager that does not work.
     * Reads :
     * /hasSession/isLoggedIn/likeThePost
     * ? postId=123
     *
     * Returns :
     * {
     *     STATUS : OK | ERROR
     *     ERROR_MESSAGE : ""
     *     users : {
     *         liked : [
     *             1,2,3    (userId)
     *         ],
     *         disliked : [
     *              4,5     (userId)
     *         ]
     *     }
     * }
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);
        PostLikeManager postLikeManager = (PostLikeManager) request.getServletContext().getAttribute(ContextKeys.POST_LIKE_MANAGER);
        String jsonRequest = ServletUtils.readFromRequest(request);
        ObjectNode responseNode = objectMapper.createObjectNode();
        JsonNode requestNode = objectMapper.readTree(jsonRequest);

        try {
            long postId = Long.parseLong(request.getParameter("postId"));
            List<PostLike> postLikes = postLikeManager.getByPost(postId);

            ArrayNode likesArray = objectMapper.createArrayNode();
            ArrayNode dislikesArray = objectMapper.createArrayNode();
            ObjectNode usersNode = objectMapper.createObjectNode();
            for(PostLike postLike : postLikes) {
                if(postLike.isLiked()) {
                    likesArray.add(postLike.getUserID());
                } else {
                    dislikesArray.add(postLike.getUserID());
                }
            }
            usersNode.set("liked", likesArray);
            usersNode.set("disliked", dislikesArray);
            responseNode.set("users", usersNode);
            JacksonUtils.addStatusOk(responseNode);
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseNode, e.getMessage());
        }

        response.getWriter().write(responseNode.toString());
    }

    /**
     * TESTED, WORKS.
     * Adds likes to post.
     * if already added, this inverts the like to dislike.
     * userId is deduced from sessionId.
     *
     * Reads :
     * {
     *     postId : 123 (post id)
     * }
     *
     * Returns :
     * {
     *     STATUS : OK | ERROR
     *     ERROR_MESSAGE : ""
     * }
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        String jsonRequest = ServletUtils.readFromRequest(request);
        PostLikeManager postLikeManager = (PostLikeManager) request.getServletContext().getAttribute(ContextKeys.POST_LIKE_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        ObjectNode responseNode = objectMapper.createObjectNode();
        JsonNode requestNode = objectMapper.readTree(jsonRequest);

        try {
            long userId = sessionManager.getSession(request.getHeader("sessionId")).getUserId();
            long postId = requestNode.get("postId").asLong();
            PostLike postLike = postLikeManager.getByUserAndPost(userId,postId);
            if(postLike == null) {
                postLike = PostLikeFactory.createNew(userId,postId,true);
                postLikeManager.rateFirstTime(postLike);
            } else {
                postLike.setLiked(!postLike.isLiked());
                postLikeManager.rateNextTime(postLike);
            }
            JacksonUtils.addStatusOk(responseNode);
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseNode, e.getMessage());
        }

        response.getWriter().write(responseNode.toString());
    }
}
