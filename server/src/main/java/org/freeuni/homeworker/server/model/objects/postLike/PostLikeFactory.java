package org.freeuni.homeworker.server.model.objects.postLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Static factory class. every "new" opeation goes here.
 * can also create lists when needed.
 */
public class PostLikeFactory {
    /**
     * Makes a single object from resultSet.
     * @param resultSet resultSet of the object
     * @return object on successful conversion, null otherwise
     */
    public static PostLike fromResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        try {
            resultSet.next();
            PostLike postLike = new PostLike();
            postLike.setId(resultSet.getLong("id"));
            postLike.setUserID(resultSet.getLong("userId"));
            postLike.setPostID(resultSet.getLong("postId"));
            postLike.setLiked(resultSet.getBoolean("liked"));

            return postLike;
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
    public static List<PostLike> listFromResultSet(ResultSet resultSet) {
        List<PostLike> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(fromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
