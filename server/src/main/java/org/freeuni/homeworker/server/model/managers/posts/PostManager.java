package org.freeuni.homeworker.server.model.managers.posts;

import org.freeuni.homeworker.server.model.objects.post.Post;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Handles post insert/update/delete into the
 * database.
 *
 * Author : Tornike Onoprishvili
 */
public interface PostManager {
    /**
     * Adds a selected post into the database
     * @param post post to add
     * @return
     */
    long add(Post post) throws SQLException, InterruptedException;

    /**
     * returns a post object with the given id.
     *
     * @param post_id id of post
     * @return post object if such exists in the DB, null otherwise
     */
    Post getById(long post_id) throws InterruptedException, SQLException;

    /**
     * returns all posts that a single user had posted.
     *
     * @param user_id user id
     * @return list of that user's posts
     */
    List<Post> getPostsByUser(long user_id) throws SQLException, InterruptedException;

    /**
     * returns all posts that were created between those two timestamps
     *
     * @param start start time (inclusive)
     * @param end end time (exclusive)
     * @return list of posts between those two timestamps
     */
    List<Post> getPostsBetweenTimes(Timestamp start, Timestamp end) throws InterruptedException, SQLException;

    /**
     * updates the content of the posts.
     *
     *
     */

    /**
     * Updates existing post in database
     *
     * @param post post object to update the exsiting post in db.
     *             note that only the contents are updated this way.
     *             post.id is used as to know which post to update.
     */
    void updatePostContents(Post post) throws InterruptedException, SQLException;

    /**
     * Returns all posts in database
     * @return list of all posts
     */
    List<Post> getAllPosts() throws SQLException, InterruptedException;


    /**
     * Return Posts Filtered By Parameters
     * @param id post Unique Id (Optional)
     * @param userId user Id (Optional)
     * @param categoryId categoryId (Optional)
     * @return List Of Posts
     * @throws InterruptedException Thrown If Interrupted
     * @throws SQLException Thrown If SQL Error
     */
    List<Post> getPosts(Long id, Long userId, Long categoryId) throws InterruptedException, SQLException;
}
