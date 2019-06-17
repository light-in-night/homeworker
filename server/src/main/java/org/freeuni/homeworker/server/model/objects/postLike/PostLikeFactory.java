package org.freeuni.homeworker.server.model.objects.postLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostLikeFactory {

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
