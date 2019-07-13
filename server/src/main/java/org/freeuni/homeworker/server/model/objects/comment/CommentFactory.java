package org.freeuni.homeworker.server.model.objects.comment;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentFactory {
    private static final Logger log = LoggerFactory.getLogger(CommentFactory.class);

    /**
     * Makes a single object from resultSet.
     * @param resultSet resultSet of the object
     * @return object on successful conversion, null otherwise
     */
    public static Comment fromResultSet(ResultSet resultSet) throws SQLException {
        Comment post = new Comment();
        post.setId(resultSet.getLong("id"));
        post.setUserId(resultSet.getLong("userId"));
        post.setPostId(resultSet.getLong("postId"));
        post.setContents(resultSet.getString("contents"));
        return post;
    }
    /**
     * Makes a list of objects from resultSet.
     * @param resultSet resultSet of the object
     * @return list of objects on successful conversion, null otherwise
     */
    public static List<Comment> listFromResultSet(ResultSet resultSet) throws SQLException {
        List<Comment> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(fromResultSet(resultSet));
        }
        return result;
    }

    /**
     * Wraps post object in objectNode and returns node.
     *
     * @param post post object that you want to convert add to given ObjectNode
     * @param node the given objectNode that you want to use
     * @return filled node
     */
    public static ObjectNode toObjectNode(Comment post, ObjectNode node) {
        node.put("id", post.getId());
        node.put("userId", post.getUserId());
        node.put("postId", post.getPostId());
        node.put("contents", post.getContents());
        return node;
    }

}
