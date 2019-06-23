package org.freeuni.homeworker.server.model.managers.posts;

import org.freeuni.homeworker.server.controller.servlets.UserRegistrationServlet;
import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class PostManagerSQL extends GeneralManagerSQL implements PostManager {

    private static final String ADD_POST =
            "INSERT INTO posts (userId, contents) \n" +
                    "VALUES \n" +
                    "(?,?);";
    private static final String SELECT_BY_ID =
            "SELECT *\n" +
                    "    FROM posts\n" +
                    "    WHERE posts.id = ?;";
    private static final String SELECT_BY_USER_ID =
            "SELECT *\n" +
                    "    FROM posts\n" +
                    "    WHERE posts.userId = ?;";
    private static final String SELECT_BETWEEN_TIMES =
            "SELECT *\n" +
                    "FROM posts\n" +
                    "WHERE posts.creationtimestamp >= ? AND posts.creationtimestamp < ?;";

    private static final String SELECT_ALL =
            "SELECT *" +
                    "FROM homeworker.posts;";

    private static Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);

    public PostManagerSQL(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    /**
     * Adds the given post to the
     * Underlying MySQL database
     *
     * @param post post object to add.
     * @return id of the inserted post
     */
    @Override
    public long add(Post post) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_POST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, post.getUserId());
            preparedStatement.setString(2, post.getContents());
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
     * Returns single post with given post id
     *
     * @param post_id posts's id
     * @return one post with given id
     */
    @Override
    public Post getById(long post_id) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                log.info("Server is stopped can't get more data.");
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, post_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.fromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    /**
     * Returns every post created by user with
     * given user_id
     *
     * @param user_id author's id
     * @return List of all posts by user
     */
    @Override
    public List<Post> getPostsByUser(long user_id) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                log.info("Server is stopped can't persist more data.");
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID);
            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    /**
     * Returns list of posts created at time T
     * such that start >= T > end
     *
     * @param start inclusive
     * @param end   exclusive
     * @return List of posts created within interval
     */
    @Override
    public List<Post> getPostsBetweenTimes(Timestamp start, Timestamp end) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                log.info("Server is stopped can't persist more data.");
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BETWEEN_TIMES);
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    /**
     *
     * TODO: correct this.
     * @param post_id
     * @param correctedContains
     */
    @Override
    public void updatePostContents(long post_id, String correctedContains) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                log.info("Server is stopped can't persist more data.");
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, post_id);
            preparedStatement.setString(1, correctedContains);
            preparedStatement.executeUpdate();
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<Post> getAllPosts() throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            if (connection == null) {
                log.info("Server is stopped can't persist more data.");
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

}