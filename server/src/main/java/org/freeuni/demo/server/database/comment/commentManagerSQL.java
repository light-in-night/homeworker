package org.freeuni.demo.server.database.comment;

import org.freeuni.demo.server.database.source.SQLConnectionFactory;

import java.sql.*;

public class commentManagerSQL{


    public void addComment(long id,long postId, String text) throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO comment VALUES ? ? ?");
        preparedStatement.setLong(1,id);
        preparedStatement.setLong(2, postId);
        preparedStatement.setString(3,text);
        preparedStatement.execute();
    }
    public void editComment(long id, String text)throws SQLException {
        Connection connection = SQLConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE comment SET commentText= ? WHERE id = ?");
        preparedStatement.setString(1,text);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
    }
    public void deleteComments(long id) {

    }
}
