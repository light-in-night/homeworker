package org.freeuni.homeworker.server.model.objects.postCategory;

import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
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
@SuppressWarnings("WeakerAccess")
public class PostCategoryFactory {

    private static final Logger log = LoggerFactory.getLogger(PostCategoryFactory.class);

    /**
     * Makes a single object from resultSet.
     * @param resultSet resultSet of the object
     * @return object on successful conversion, null otherwise
     */
    public static PostCategory postCategoryFromResultSet(ResultSet resultSet)  {
        try {
            PostCategory postCategory = new PostCategory();
            postCategory.setId(resultSet.getLong("id"));
            postCategory.setPostId(resultSet.getLong("postId"));
            return postCategory;
        } catch (SQLException e) {
            log.error("Error occurred during transforming result set to post category.", e);
        }
        return null;
    }

    /**
     * Makes a list of objects from resultSet.
     * @param resultSet resultSet of the object
     * @return list of objects on successful conversion, null otherwise
     */
    public static List<PostCategory> listFromResultSet(ResultSet resultSet) {
        List<PostCategory> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(postCategoryFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.info("Error occurred during transforming result set to list of post category list.");
        }
        return null;
    }
}
