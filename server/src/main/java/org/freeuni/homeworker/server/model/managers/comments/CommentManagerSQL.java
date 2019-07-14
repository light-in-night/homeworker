package org.freeuni.homeworker.server.model.managers.comments;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.comment.Comment;
import org.freeuni.homeworker.server.model.objects.comment.CommentFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.*;
import java.util.List;

public class CommentManagerSQL  extends GeneralManagerSQL implements CommentManager{
    private static final String ADD_COMMENT =
            "INSERT INTO comment (userId, postId, contents) \n" +
                    "VALUES \n" +
                    "(?,?,?);";
    private static final String SELECT_BY_ID =
            "SELECT *\n" +
                    "    FROM comment\n" +
                    "    WHERE comment.id = ?;";
    private static final String SELECT_BY_USER_ID =
            "SELECT *\n" +
                    "    FROM comment\n" +
                    "    WHERE comment.userId = ?;";
    private static final String SELECT_BY_POST_ID =
            "SELECT *\n" +
                    "    FROM comment\n" +
                    "    WHERE comment.postId = ?" +
                    "    LIMIT ?;";

    private static final String SELECT_ALL =
            "SELECT *" +
                    "FROM homeworker.comment;";

    private static final String COUNT_POST_COMMENT = "SELECT COUNT(*) FROM comment WHERE postId = ?";

    public CommentManagerSQL(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    /**
     * Adds the given post to the
     * Underlying MySQL database
     *
     * @param comment  object to add.
     * @return id of the inserted post
     */
    @Override
    public long add(Comment comment) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, comment.getUserId());
            preparedStatement.setLong(2, comment.getPostId());
            preparedStatement.setString(3, comment.getContents());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }
    /**
     * Returns single comment with given comment id
     *
     * @param comment_id posts's id
     * @return one post with given id
     */
    @Override
    public Comment getById(long comment_id) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, comment_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CommentFactory.fromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }
    /**
     * Returns every comment created by user with
     * given user_id
     *
     * @param user_id author's id
     * @return List of all posts by user
     */
    @Override
    public List<Comment> getCommentsByUser(long user_id) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID);
            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CommentFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    /**
     * Returns every comment created by user with
     * given user_id
     *
     * @param post_id author's id
     * @param commentNum
	 * @return List of all posts by user
     */
    @Override
    public List<Comment> getCommentsByPost(long post_id, long commentNum) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_POST_ID);
            preparedStatement.setLong(1, post_id);
            preparedStatement.setLong(2, commentNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CommentFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<Comment> getAllPosts() throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CommentFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }



    @Override
    public long numberOfPostComments(long id) throws InterruptedException, SQLException {
        Connection connection = null;
        try{
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_POST_COMMENT);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } finally {
            if(connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }


}
