package org.freeuni.homeworker.server.model.managers.comments;

import org.freeuni.homeworker.server.model.objects.comment.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentManager {
    /**
     * Adds a selected post into the database
     * @param comment post to add
     * @return
     */
    long add(Comment comment) throws SQLException, InterruptedException;

    /**
     * returns a comment object with the given id.
     *
     * @param comment_id id of post
     * @return post object if such exists in the DB, null otherwise
     */
    Comment getById(long comment_id) throws InterruptedException, SQLException;

    /**
     * returns all comments that a single user had made.
     *
     * @param user_id user id
     * @return list of that user's comments
     */
    List<Comment> getCommentsByUser(long user_id) throws SQLException, InterruptedException;

    /**
     * returns all comments that a single post had
     *
     * @param post_id user id
     * @param commentNum Number Of Comments
	 * @return list of that post's comments
     */
    List<Comment> getCommentsByPost(long post_id, long commentNum) throws SQLException, InterruptedException;


    /**
     * Returns all comments in database
     * @return list of all posts
     */
    List<Comment> getAllPosts() throws SQLException, InterruptedException;



    /**
     * Returns Number Of Post Comments
     * @param id post Unique Id
     * @return Quantity
     */
    long numberOfPostComments(long id) throws InterruptedException, SQLException;
}
