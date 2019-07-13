package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.comments.CommentManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.comment.Comment;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CommentCreateServlet")
public class CommentCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        CommentManager commentManager = (CommentManager) getServletContext().getAttribute(ContextKeys.COMMENT_MANAGER);
        SessionManager sessionManager = (SessionManager) request.getServletContext().getAttribute(ContextKeys.SESSION_MANAGER);

        String jsonStr = ServletUtils.readFromRequest(request);
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        ObjectNode returnObjectNode = objectMapper.createObjectNode();

        Comment comment = objectMapper.readValue(rootNode.get("comment").toString(),Comment.class);
        Session userSession = sessionManager.getSession(request.getHeader("sessionId"));

        try{
            comment.setUserId(userSession.getUserId());
            long postId = commentManager.add(comment);
            returnObjectNode.put("id", postId);
        } catch (Exception e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(returnObjectNode, e.toString());
        }
        response.getWriter().write(returnObjectNode.toString());

    }

}
