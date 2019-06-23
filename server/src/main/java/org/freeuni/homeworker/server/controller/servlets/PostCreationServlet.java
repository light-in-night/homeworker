package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PostCreationServlet", urlPatterns = "/createpost")
public class PostCreationServlet extends HttpServlet {

    /**
     * This servlet creates posts.
     * to use it, you must send JSON in this format:
     *  {
     *      "userId" : 123456,
     *      "contents" : "hello world"
     *  }
     *
     *  has an author with id of 123456
     *  and contents of "hello world"
     *
     *  this servlet returns this JSON
     *  {
     *      status : "OK"
     *      errorMessage : ""
     *      id : 123 (this is postId)
     *  }
     *
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);

        String jsonStr = ServletUtils.readFromRequest(request);
        Post post = objectMapper.readValue(jsonStr,Post.class);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
             long postId = postManager.add(post);
             objectNode.put("id", postId);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }
        response.getWriter().write(objectNode.toString());
    }
}
