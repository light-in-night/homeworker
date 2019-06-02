package org.freeuni.demo.server.database.managers;

import org.freeuni.demo.server.database.models.Creator;
import org.freeuni.demo.server.database.models.Post;
import org.freeuni.demo.server.database.source.SQLConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostManagerSQL implements PostManager {

    private final SQLConnectionFactory conFact;

    private final String ADD_POST_SQL =
            "INSERT INTO posts (?, ?, ?)\n" +
            "VALUES (?, ?, ?);";
    private final String GET_BY_USER_SQL =
            "SELECT posts.postId as post_id,\n" +
            "       posts.content as content,\n" +
            "       users.userId as user_id,\n" +
            "       users.name as user_name\n" +
            "FROM posts\n" +
            "JOIN users\n" +
            "ON posts.userId_FK = users.userId\n" +
            "WHERE posts.userId_FK = ?;";

    private final Creator creator;

    PostManagerSQL(SQLConnectionFactory conFact, Creator creator) {
        this.conFact = conFact;
        this.creator = creator;
    }

    @Override
    public void addPost(Post pst) {
        try(Connection con = conFact.getConnection()) {
            PreparedStatement ps = con.prepareStatement(ADD_POST_SQL);
            ps.setLong(1, pst.getId());
            ps.setLong(2, pst.getAuthor().getId());
            ps.setString(3, pst.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> byUserId(long user_id) {
        try(Connection con = conFact.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_BY_USER_SQL);
            ps.setLong(1, user_id);
            ResultSet rs = ps.executeQuery();
            return creator.postsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
