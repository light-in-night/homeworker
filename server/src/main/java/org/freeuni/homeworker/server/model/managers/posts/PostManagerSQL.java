package org.freeuni.homeworker.server.model.managers.posts;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String UPDATE_BY_ID =
            "UPDATE posts \n" +
                    "SET posts.contents = ? \n" +
                    "WHERE posts.id = ?;";

    private static Logger log = LoggerFactory.getLogger(PostManager.class);

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
     * @param postId posts's id
     * @return one post with given id
     */
    @Override
    public Post getById(long postId) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, postId);
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
//                log.info("Server is stopped can't persist more data.");
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
     * Updates existing post in database
     *
     * @param post post object to update the exsiting post in db.
     *             note that only the contents are updated this way.
     *             post.id is used as to know which post to update.
     */
    @Override
    public void updatePostContents(Post post) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setLong(2, post.getId());
            preparedStatement.setString(1, post.getContents());
            preparedStatement.executeUpdate();
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<Post> getAllPosts() throws SQLException, InterruptedException {
        return getPosts(SELECT_ALL);
    }

    @Override
    public List<Post> getPosts(Long id, Long userId, Long categoryId) throws InterruptedException, SQLException {
        String sql = generateSQLStringForSelect(id, userId, categoryId);
        return getPosts(sql);
    }

    /**
     * Takes Posts From Database Given Statement String
     * @param sql SQL Statements String
     * @return List Of Posts
     * @throws InterruptedException ex
     * @throws SQLException ex
     */
    private List<Post> getPosts(String sql) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    /**
     * Generates SQL String
     * If Any Parameter Is Not, It Will Be Excluded
     * From SQL Statement
     * @param id post Id
     * @param userId user Id
     * @param categoryId Category Id
     * @return SQL Statement String
     */
    private String generateSQLStringForSelect(Long id, Long userId, Long categoryId) {
        StringBuilder getUsers = new StringBuilder("SELECT * FROM posts p ");
        Map<String, Long> properties = new HashMap<>();

        if(categoryId == null){
            getUsers.append("WHERE 1 = 1 ");
        } else {
            getUsers.append("LEFT JOIN postcategory c ON p.id = c.postId ");
            getUsers.append("WHERE 1 = 1 ");
        }

        if(categoryId != null){
            properties.put("c.categoryId", categoryId);
        }

        if(id != null){
            properties.put("p.id", id);
        }

        if(userId != null){
            properties.put("p.userId", userId);
        }

        for(String elem : properties.keySet()){
            getUsers.append("AND " + elem + " = " + properties.get(elem) + " ");
        }
        getUsers.append(";");
        return getUsers.toString();
    }


}