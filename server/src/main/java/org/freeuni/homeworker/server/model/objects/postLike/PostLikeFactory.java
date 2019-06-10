package org.freeuni.homeworker.server.model.objects.postLike;

import org.freeuni.homeworker.server.model.objects.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostLikeFactory {

    public static PostLike fromResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        try {
            resultSet.next();
            PostLike postLike = new PostLike();
            postLike.setId(resultSet.getLong(1));
            postLike.setUserID(resultSet.getLong(2));
            postLike.setPostID(resultSet.getLong(3));
            postLike.setLiked(resultSet.getBoolean(4));

            return postLike;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
