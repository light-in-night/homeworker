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
    private final String GET_BY_USERID_AND_POSTID = "SELECT * FROM postLike WHERE userId = ? AND postId = ?;";
    private final String ADD_QUERY = "INSERT INTO postLike (userID, postID, liked) VALUES (?, ?, ?);";
    private final String MODIFY_QUERY = "UPDATE postLike SET liked=? WHERE postId=?;";
    private final String COUNT_POST_LIKE = "SELECT COUNT(*) FROM postLike WHERE postId = ?";

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

}
