package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.postLike.PostLikeFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostLikeManagerSQL extends GeneralManagerSQL implements PostLikeManager {

    private static final String GET_BY_POSTID = "SELECT * FROM postLike WHERE postId = ?;";
    private static final String GET_BY_USERID_AND_POSTID = "SELECT * FROM postLike WHERE userId = ? AND postId = ?;";
    private static final String ADD_QUERY = "INSERT INTO postLike (userID, postID, liked) VALUES (?, ?, ?);";
    private static final String MODIFY_QUERY = "UPDATE postLike SET liked=? WHERE postId=?;";
    private static final String COUNT_POST_LIKE = "SELECT COUNT(*) FROM postLike WHERE postId = ? AND liked = 1";
    private static final String COUNT_POST_UNLIKE = "SELECT COUNT(*) FROM postLike WHERE postId = ? AND liked = 0";
    private static final String DELETE_QUERY = "DELETE FROM postLike WHERE userID = ? AND postID = ?";

    public PostLikeManagerSQL(ConnectionPool connectionsPool){
        super(connectionsPool);
    }




    @Override
    public void rateFirstTime(PostLike postLikeObject) throws InterruptedException, SQLException {
        Connection con = null;
        try {
            con = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(ADD_QUERY);
            preparedStatement.setLong(1, postLikeObject.getUserID());
            preparedStatement.setLong(2, postLikeObject.getPostID());
            preparedStatement.setBoolean(3, postLikeObject.isLiked());
            preparedStatement.executeUpdate();
        } finally {
            if(con != null) {
                connectionPool.putBackConnection(con);
            }
        }
    }

    @Override
    public PostLike getByUserAndPost(long userId, long postId) throws SQLException, InterruptedException {
        Connection con = null;
        try {
            con = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(GET_BY_USERID_AND_POSTID);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostLikeFactory.fromResultSet(resultSet);
        } finally {
            if(con != null) {
                connectionPool.putBackConnection(con);
            }
        }
    }

    @Override
    public void rateNextTime(PostLike postLike) throws InterruptedException, SQLException {
        Connection con = null;
        try {
            con = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(MODIFY_QUERY);
            preparedStatement.setBoolean(1, postLike.isLiked());
            preparedStatement.setLong(2, postLike.getId());
            preparedStatement.executeUpdate();
        } finally {
            if(con != null) {
                connectionPool.putBackConnection(con);
            }
        }
    }

    @Override
    public List<PostLike> getByPost(long postId) throws InterruptedException, SQLException {
        Connection con = null;
        try {
            con = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(GET_BY_POSTID);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostLikeFactory.listFromResultSet(resultSet);
        } finally {
            if(con != null) {
                connectionPool.putBackConnection(con);
            }
        }
    }

    @Override
    public long numberOfPostLikes(long id) throws InterruptedException, SQLException {
        Connection connection = null;
        try{
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_POST_LIKE);
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

    @Override
    public long numberOfPostUnLikes(long id) throws InterruptedException, SQLException {
        Connection connection = null;
        try{
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_POST_UNLIKE);
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

    public boolean hasLiked(long userId, long postID){
        String sql = "SELECT COUNT(*) FROM postLike WHERE userID = ? AND postID = ?";
        Connection connection = null;
        try{
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, postID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1) != 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
        return false;
    }




    /**
     * Adds Like Information In DataBase
     * @param postLikeObject Like Information(userID, postID)
     * @return true If Added Successfully
     */
    public boolean like(PostLike postLikeObject) {
        boolean queryExecuted;
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            executeAddSQLQuery(postLikeObject, ADD_QUERY, connection);
            queryExecuted = true;
        } catch (Exception e) {
            e.printStackTrace();
            queryExecuted = false;
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
        return queryExecuted;
    }

    /**
     * Executes Given Query
     * @param postLikeObject Information To Based On To Execute(postId, UserID)
     * @param addQuery query
     */
    private void executeAddSQLQuery(PostLike postLikeObject, String addQuery, Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
        preparedStatement.setLong(1, postLikeObject.getUserID());
        preparedStatement.setLong(2, postLikeObject.getPostID());
        preparedStatement.setBoolean(3,postLikeObject.isLiked());
        preparedStatement.execute();
    }

    /**
     * Removes Post Like Information From DataBase
     * @param postLikeObject UnLike Information(userID, postID)
     * @return true If Query Executed Successfully
     */
    public boolean unLike(PostLike postLikeObject) {
        boolean queryExecuted;
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            executeDeleteSQLQuery(postLikeObject, DELETE_QUERY, connection);
            queryExecuted = true;
        } catch (Exception e) {
            e.printStackTrace();
            queryExecuted = false;
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
        return queryExecuted;
    }

    /**
     * Executes Delete Query
     * @param postLikeObject
     * @param delete_query
     * @param connection
     * @throws SQLException
     */
    private void executeDeleteSQLQuery(PostLike postLikeObject, String delete_query, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(delete_query);
        preparedStatement.setLong(1,postLikeObject.getUserID());
        preparedStatement.setLong(2, postLikeObject.getPostID());
        preparedStatement.execute();
    }


}
