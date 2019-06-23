package org.freeuni.homeworker.server.model.objects.post;

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
    public static Post fromResultSet(ResultSet resultSet)  {
        try {
            Post post = new Post();
            post.setId(resultSet.getLong("id"));
            post.setUserId(resultSet.getLong("userId"));
            post.setContents(resultSet.getString("contents"));
            post.setCreationTimestamp(resultSet.getTimestamp("creationTimestamp"));
            return post;
        } catch (SQLException e) {
            log.error("Invalid result set was passed to post factory.", e);
        }
        return null;
    }
    /**
     * Makes a list of objects from resultSet.
     * @param resultSet resultSet of the object
     * @return list of objects on successful conversion, null otherwise
     */
    public static List<Post> listFromResultSet(ResultSet resultSet) {
        List<Post> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(fromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.error("Error occurred during transforming result set into list of posts.", e);
        }
        return null;
    }
}
