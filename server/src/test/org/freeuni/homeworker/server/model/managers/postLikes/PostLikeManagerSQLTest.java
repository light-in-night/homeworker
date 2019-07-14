package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostLikeManagerSQLTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    private PostLike postLike;
    private PostLike postLike2;
    private PostLike postUnlike;

    @Before
    public void setUp() throws InterruptedException, SQLException {
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        postLike = new PostLike((long)1,(long)2,(long)3,true);
        postLike2 = new PostLike((long)2,(long)4,(long)3,true);
        postUnlike = new PostLike((long)1,(long)4,(long)2,false);

        when(resultSet.getLong("id")).thenReturn(postLike.getId()).thenReturn(postLike2.getId());
        when(resultSet.getLong("userId")).thenReturn(postLike.getUserID()).thenReturn(postLike2.getUserID());
        when(resultSet.getLong("postId")).thenReturn(postLike.getPostID()).thenReturn(postLike2.getPostID());
        when(resultSet.getBoolean("liked")).thenReturn(postLike.isLiked()).thenReturn(postLike2.isLiked());
        when(resultSet.getLong(1)).thenReturn((long) 1);

    }
    @Test
    public void rateFirstTime() throws InterruptedException, SQLException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateFirstTime(postLike);
    }

    @Test
    public void getByUserAndPost() throws SQLException, InterruptedException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateFirstTime(postLike);
        PostLike help = postLikeManagerSQL.getByUserAndPost(2,3);
        assertEquals(help.isLiked(),postLike.isLiked());
        assertEquals(help.getUserID(),postLike.getUserID());
        assertEquals(help.getPostID(),postLike.getPostID());
        assertEquals(help.getId(),postLike.getId());
    }

    @Test
    public void rateNextTime() throws SQLException, InterruptedException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateNextTime(postLike);

    }

    @Test
    public void getByPost() throws SQLException, InterruptedException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateFirstTime(postLike);
        postLikeManagerSQL.rateFirstTime(postLike2);
        List<PostLike> helpList = postLikeManagerSQL.getByPost(1);
        List<PostLike> likes = new ArrayList<>();
        likes.add(postLike);
        likes.add(postLike2);
        for(int i=0; i<helpList.size(); i++){
            PostLike help = helpList.get(i);
            PostLike cur = likes.get(i);
            assertEquals(help.isLiked(),cur.isLiked());
            assertEquals(help.getUserID(),cur.getUserID());
            assertEquals(help.getPostID(),cur.getPostID());
            assertEquals(help.getId(),cur.getId());

        }

    }

    @Test
    public void numberOfPostLikes() throws SQLException, InterruptedException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateFirstTime(postLike);
        long number = postLikeManagerSQL.numberOfPostLikes(1);
        assertEquals(number,1);
    }

    @Test
    public void numberOfPostUnLikes() throws SQLException, InterruptedException {
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.rateFirstTime(postUnlike);
        postLikeManagerSQL.rateFirstTime(postLike);
        postLikeManagerSQL.rateFirstTime(postLike2);
        long number = postLikeManagerSQL.numberOfPostUnLikes(1);
        assertEquals(number,1);
    }


}