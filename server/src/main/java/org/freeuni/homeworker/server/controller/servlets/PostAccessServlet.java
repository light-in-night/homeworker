package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostAccessServlet", urlPatterns = {"/getpost"})
public class PostAccessServlet extends HttpServlet {

    /**
     * Returns every post from Database.
     * requires no arguments to be passed in.
     * The returned JSON will have a format
     * for example like this:
     * example : {
     * STATUS : "OK",
     * ERROR_MESSAGE : "",
     *
     *  posts :
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
     *  basically, we have this structure:
     *  {
     *     status :""
     *     error_message: ""
     *     posts :[
     *      {postJson_0},
     *      {postJson_1},
     *      {postJson_2},
     *      ...
     *      ]
     *  }
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        PostManager postManager = (PostManager) request.getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        List<Post> postList = null;
        try {
            postList = postManager.getAllPosts();
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
