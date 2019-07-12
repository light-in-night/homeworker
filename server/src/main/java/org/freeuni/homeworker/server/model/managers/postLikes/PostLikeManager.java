package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.postLike.PostLike;

import java.sql.SQLException;
import java.util.List;

/**
 * Manages the postLike entries in the database
 *
 * Author : Tornike kechakhmadze
 */
public interface PostLikeManager {

    void rateFirstTime(PostLike postLikeObject) throws InterruptedException, SQLException; // User Like Method

    PostLike getByUserAndPost(long userId, long postId) throws SQLException, InterruptedException;

    void rateNextTime(PostLike postLike) throws InterruptedException, SQLException;

    List<PostLike> getByPost(long postId) throws InterruptedException, SQLException;
}