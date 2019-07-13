package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Guga Tkeshelade,Tornike Onoprishvili
 * Tested via : SoapUI
 */
@WebServlet(name = "PostAccessServlet", urlPatterns = {"/posts"})
public class PostAccessServlet extends HttpServlet {

    /**
     * Gets all posts.
     * can be filtered using arguments.
     *
     * Reads:
     * /posts
     * ?id=123              (OPTIONAL PARAM)
     * & userId=123         (OPTIONAL PARAM)
     * & categoryId=123     (OPTIONAL PARAM)
     * & t0 = 1234          (OPTIONAL PARAM) (timestamp start)
     * & t1 = 12345         (OPTIONAL PARAM) (timestamp end)
     *
     * OPTIONAL PARAMS :
     * if any of the params (or any combination of them) are
     * not null and are valid (parsable) then the returned array of posts
     * will be filtered by them.
     *
     * OPTIONAL PARAMS : if any are selected,
     * The resulting list of categories will be such that
     * they have the given parameters.
     * If you want to filter posts by userId, categoryId, or just Id,
     * you must specify it in JSON params.
     *
     * Returns :
     * {
     *      STATUS : "OK" | "ERROR"
     *      ERROR_MESSAGE : "",
     *      posts :
     *      [{
     *          "id" : 1234,
     *          "userId" : 123,
     *          "contents" : "hello world",
     *          "creationTimestamp" : 31234124 (just google unix epoch)
     *      },
     *      {
     *          "id" : 13312,
     *          "userId" : 123,
     *          ...
     *      }
     *      ...
     *      ]
     *  }
     *
     *  Access to this method is not filtered.
     *  It can be accessed freely.
     *
     *  Author : Tornike onoprishvili
     *  Tested via : SOAPUI. (EVERY METHOD)
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCORSHeaders(response);
        ServletUtils.setJSONContentType(response);

        PostManager postManager = (PostManager) request.getServletContext().getAttribute(ContextKeys.POST_MANAGER);
        ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
        PostCategoryManager postCategoryManager = (PostCategoryManager) request.getServletContext().getAttribute(ContextKeys.POST_CATEGORY_MANAGER);
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            List<Post> postList = postManager.getAllPosts()
                    .stream()
                    .filter(post -> request.getParameter("id") == null || post.getId() == Long.parseLong(request.getParameter("id")))
                    .filter(post -> request.getParameter("userId") == null || post.getUserId() == Long.parseLong(request.getParameter("userId")))
                    .filter(post -> {
                        try {
                            return request.getParameter("categoryId") == null ||
                                    postCategoryManager.getByPostId(post.getId()).getCategoryId()
                                            == Long.parseLong(request.getParameter("categoryId"));
                        } catch (Exception e) { e.printStackTrace(); }
                        return false;
                    })
                    .filter(post -> request.getParameter("t0") == null ||  (post.getCreationTimestamp().getTime() >=  Long.parseLong(request.getParameter("t0"))))
                    .filter(post -> request.getParameter("t1") == null || (post.getCreationTimestamp().getTime() <  Long.parseLong(request.getParameter("t1"))))
                    .collect(Collectors.toList());
            ArrayNode postArrayNode = objectMapper.createArrayNode();
            for(Post post : postList) {
                postArrayNode.add(PostFactory.toObjectNode(post, objectMapper.createObjectNode()));
            }
            objectNode.set("posts", postArrayNode);
            JacksonUtils.addStatusOk(objectNode);
        } catch (Exception e) {
            e.printStackTrace();
            JacksonUtils.addStatusError(objectNode, e.toString());
        }

        response.getWriter().write(objectNode.toString());
    }

}
