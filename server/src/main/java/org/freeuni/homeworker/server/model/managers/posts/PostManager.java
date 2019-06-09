package org.freeuni.homeworker.server.model.managers.posts;

import org.freeuni.homeworker.server.model.managers.AbstractManager;
import org.freeuni.homeworker.server.model.objects.post.Post;

import java.sql.Timestamp;
import java.util.List;

/**
 * Handles post insert/update/delete into the
 * database.
 */
public interface PostManager extends AbstractManager {
    void addPost(Post post);

    Post getPostById(long post_id);

    List<Post> getPostsByUser(long user_id);

    List<Post> getPostsBetweenTimes(Timestamp start, Timestamp end);

    void updatePostContents(long post_id, String correctedContains);

    void updatePostRating(long post_id, long diff);
}
