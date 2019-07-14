package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.comments.CommentManager;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.comment.Comment;
import org.freeuni.homeworker.server.model.objects.comment.CommentFactory;
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
import java.util.List;


@WebServlet(name = "CommentAccessServlet", urlPatterns = "/getComments")
public class CommentAccessServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CommentAccessServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        CommentManager commentManager = (CommentManager) request.getServletContext().getAttribute(ContextKeys.COMMENT_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode responseNode = objectMapper.createObjectNode();

        try{
            long commentNum;
            try {
                commentNum = Long.parseLong(request.getParameter("numComments"));
            } catch (Exception e) {
                commentNum = 1; // Default
            }

            long postId = Long.parseLong(request.getParameter("postId"));

            List<Comment> comments = commentManager.getCommentsByPost(postId, commentNum);
            ArrayNode commentArray = objectMapper.createArrayNode();
            for(Comment comment : comments){
                commentArray.add(CommentFactory.toObjectNode(comment,objectMapper.createObjectNode()));
            }
            responseNode.set("comments", commentArray);
            JacksonUtils.addStatusOk(responseNode);
        } catch (Exception e) {
            JacksonUtils.addStatusError(responseNode, e.getMessage());
            log.error("Error occurred during retrieval of the comments.", e);
        }

        response.getWriter().write(responseNode.toString());

    }
}
