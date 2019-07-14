package org.freeuni.homeworker.server.model.managers.posts;

import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostManagerSQLTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    private Post post;
    private Post post2;
    Timestamp start;

    @Before
    public void setUp() throws InterruptedException, SQLException {
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(connection.prepareStatement(any(String.class),anyInt())).thenReturn(preparedStatement);
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        long ret =1;
        when(resultSet.getLong(1)).thenReturn(ret);

        post = new Post(1,2,"selling");
        post2 = new Post(2,2,"selling");
        start =new Timestamp(System.currentTimeMillis());
        post.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        post2.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        when(resultSet.getLong("id")).thenReturn(post.getId()).thenReturn(post2.getId());
        when(resultSet.getLong("userId")).thenReturn(post.getUserId()).thenReturn(post2.getUserId());
        when(resultSet.getString("contents")).thenReturn(post.getContents()).thenReturn(post2.getContents());
        when(resultSet.getTimestamp("creationtimestamp")).thenReturn(post.getCreationTimestamp()).thenReturn(post2.getCreationTimestamp());
        //when(resultSet.getString("category")).thenReturn(post.getCategory()).thenReturn(post2.getCategory());
    }
    @Test
    public void add() throws SQLException, InterruptedException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        postManagerSQL.add(post);
    }

    @Test
    public void getById() throws InterruptedException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        postManagerSQL.add(post);
        Post help = postManagerSQL.getById(1);
        if(help.getId()!=post.getId()||help.getUserId()!=post.getUserId()||help.getContents()!=post.getContents()){
            assert(0==1);
        }
    }

    @Test
    public void getPostsByUser() throws InterruptedException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        postManagerSQL.add(post);
        postManagerSQL.add(post2);
        List<Post> help = postManagerSQL.getPostsByUser(2);
        List<Post> list = new ArrayList<>();
        list.add(post);
        list.add(post2);
        for(int i=0; i<help.size(); i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getUserId()!=list.get(i).getUserId()||help.get(i).getContents()!=list.get(i).getContents()){
                assert(0==1);
            }
        }
    }

    @Test
    public void getPostsBetweenTimes() throws InterruptedException, SQLException {
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Post> list = new ArrayList<>();
        Timestamp start =new Timestamp(System.currentTimeMillis());
        postManagerSQL.add(post);
        postManagerSQL.add(post2);
        list.add(post);
        list.add(post2);
        List<Post> help = postManagerSQL.getPostsBetweenTimes(start,(new Timestamp(System.currentTimeMillis())));
        for(int i=0; i<help.size(); i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getUserId()!=list.get(i).getUserId()||help.get(i).getContents()!=list.get(i).getContents()||help.get(i).getCreationTimestamp()!=list.get(i).getCreationTimestamp()){
                System.out.println(i);
                assert(0==1);
            }
        }


    }

    @Test
    public void updatePostContents() throws SQLException, InterruptedException {
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        postManagerSQL.add(post);
        postManagerSQL.updatePostContents(post);
    }

    @Test
    public void getAllPosts() throws SQLException, InterruptedException {
        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        postManagerSQL.add(post);
        postManagerSQL.add(post);
        List<Post> help = postManagerSQL.getAllPosts();
        List<Post> list = new ArrayList<>();
        list.add(post);
        list.add(post2);
        for(int i=0; i<help.size(); i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getUserId()!=list.get(i).getUserId()||help.get(i).getContents()!=list.get(i).getContents()){
                assert(0==1);
            }
        }
    }

    @Test
    public void getPosts() throws SQLException, InterruptedException {

        PostManagerSQL postManagerSQL = new PostManagerSQL(connectionPool);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        postManagerSQL.add(post);
        postManagerSQL.add(post);
        List<Post> help = postManagerSQL.getPosts((long)1,(long)2,(long) 1);
        List<Post> list = new ArrayList<>();
        list.add(post);
        for(int i=0; i<help.size(); i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getUserId()!=list.get(i).getUserId()||help.get(i).getContents()!=list.get(i).getContents()){
                assert(0==1);
            }
        }
    }
}