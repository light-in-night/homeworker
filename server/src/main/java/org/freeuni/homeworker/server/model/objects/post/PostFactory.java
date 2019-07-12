package org.freeuni.homeworker.server.model.objects.post;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Static factory class. every "new" opeation goes here.
 * can also create lists when needed.
 */
public class PostFactory {

    private static final Logger log = LoggerFactory.getLogger(PostFactory.class);

    /**
     * Makes a single object from resultSet.
     * @param resultSet resultSet of the object
     * @return object on successful conversion, null otherwise
     */
    public static Post fromResultSet(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setUserId(resultSet.getLong("userId"));
        post.setContents(resultSet.getString("contents"));
        post.setCreationTimestamp(resultSet.getTimestamp("creationTimestamp"));
//        post.setId(resultSet.getLong(1));
//        post.setUserId(resultSet.getLong(2));
//        post.setContents(resultSet.getString(3));
//        post.setCreationTimestamp(resultSet.getTimestamp(4));
        return post;
    }
    /**
     * Makes a list of objects from resultSet.
     * @param resultSet resultSet of the object
     * @return list of objects on successful conversion, null otherwise
     */
    public static List<Post> listFromResultSet(ResultSet resultSet) throws SQLException {
        List<Post> result = new ArrayList<>();
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
    public static ObjectNode toObjectNode(Post post, ObjectNode node) {
        node.put("id", post.getId());
        node.put("userId", post.getUserId());
        node.put("contents", post.getContents());
        node.put("creationTimestamp", post.getCreationTimestamp().getTime());
        return node;
    }
}
