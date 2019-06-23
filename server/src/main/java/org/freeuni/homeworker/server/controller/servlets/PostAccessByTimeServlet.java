package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "PostAccessByTimeServlet", urlPatterns = "/getpost/bytime")
public class PostAccessByTimeServlet extends HttpServlet {
    /**
     * Returns JSON list of posts between given two times.
     * the times are in format yyyy-[m]m-[d]d hh:mm:ss[.f...]. format. for example '2019-1-5 23:12:31'.
     * you must provide two parameters : startTime, endTime, both having above said formats.
     * this will return JSON of list of posts :
     * example : {
     *      STATUS : "OK"
     *      ERROR_MESSAGE : ""
     *      posts : [{
     *          "id" : 1234,
     *          "userId" : 123,
     *          "content" : "hello world",
     *          "creationtimestamp" : 31234124 (just google unix epoch)
     *      },
     *      {
     *          "id" : 13312,
     *          "userId" : 123,
     *          ...
     *      }
     *      ...
     *      ]
     *  }
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
            Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
            List<Post> postList = postManager.getPostsBetweenTimes(startTime, endTime);
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for(Post post : postList) {
                arrayNode.add(objectMapper.writeValueAsString(post));
            }
            objectNode.set("posts", arrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }
        response.getWriter().write(objectNode.toString());
    }
}
