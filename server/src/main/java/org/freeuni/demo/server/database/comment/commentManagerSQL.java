package org.freeuni.demo.server.database.comment;

import org.freeuni.demo.server.database.source.SQLConnectionFactory;

import java.sql.*;
import java.sql.ResultSet;

public class commentManagerSQL{

    public void addComment(long id,long userId,long postId, String text) throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO comment VALUES (?, ?, ?, ?);");
        preparedStatement.setLong(1,id);
        preparedStatement.setLong(2, userId);
        preparedStatement.setLong(3, postId);
        preparedStatement.setString(4,text);
        preparedStatement.execute();
    }
    public void editComment(long id, String text)throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE comment SET commentText= ? WHERE id = ?");
        preparedStatement.setString(1, text);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
    }
    public void deleteComment(long id) throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
    }
    public  ResultSet getPostComments(long postId) throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comment where postId = ?");
        preparedStatement.setLong(1, postId);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }
    public ResultSet getUserComments(long postId) throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comment where userId = ?");
        preparedStatement.setLong(1, postId);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }
}
