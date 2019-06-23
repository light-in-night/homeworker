package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostAccessByUserServlet",urlPatterns = "/getpost/byuser")
public class PostAccessByUserServlet extends HttpServlet {
    /**
     *  Expects an single integer argument with a name
     *  userId.
     *  As an example it can be passed in via this url link :
     *  http//:localhost:8089/war_exploded/getPosts/byUser/?userId=123
     *
     *   the returned JSON will contain all the posts by user with id 123
     *  for example
     * example : {
     *      STATUS : "OK",
     *      ERROR_MESSAGE : ""
     *
     *      posts :
     *      [{
     *          "id" : 1234,
     *          "userId" : 123,
     *          "contents" : "hello world",
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setJSONContentType(response);
        ServletUtils.setCORSHeaders(response);

        PostManager postManager = (PostManager) getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();


        try {
            String userIdString = request.getParameter("userId");
            List<Post> postList = postManager.getPostsByUser(Long.parseLong(userIdString));
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
