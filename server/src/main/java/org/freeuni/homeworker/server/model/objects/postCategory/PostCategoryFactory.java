package org.freeuni.homeworker.server.model.objects.postCategory;

import org.freeuni.homeworker.server.model.objects.post.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Static factory class. every "new" opeation goes here.
 * can also create lists when needed.
 */
public class PostCategoryFactory {
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
            e.printStackTrace();
            return null;
        }
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
            e.printStackTrace();
            return null;
        }
    }
}
