package org.freeuni.homeworker.server.model.managers.postCategory;

import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PostCategoryManagerSQLTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    private PostCategory postCategory;
    private PostCategory postCategory2;

    @Before
    public void setUp() throws InterruptedException, SQLException {
        when(connectionPool.acquireConnection()).thenReturn(connection);

        postCategory = new PostCategory(1,2,3);
        postCategory2 = new PostCategory(2,4,3);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.getLong("id")).thenReturn(postCategory.getId()).thenReturn(postCategory2.getId());
        when(resultSet.getLong("postId")).thenReturn(postCategory.getPostId()).thenReturn(postCategory2.getPostId());
        when(resultSet.getLong("categoryId")).thenReturn(postCategory.getCategoryId()).thenReturn(postCategory2.getCategoryId());

    }

    @Test
    public void add() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.add(postCategory);
        postCategoryManagerSQL.add(postCategory);
    }

    @Test
    public void removeById() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.removeById(1);
        postCategoryManagerSQL.removeById(1);
    }

    @Test
    public void getByPostId() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.getByPostId(1);
        List<PostCategory> help = postCategoryManagerSQL.getByPostId(1);
        System.out.println(help.get(0).getId());
        System.out.println(help.get(0).getPostId());
        System.out.println(help.get(0).getCategoryId());
        if(help.get(0).getId()!=postCategory.getId()||help.get(0).getPostId()!=postCategory.getPostId()||help.get(0).getCategoryId()!=postCategory.getCategoryId()){
            assert (1==2);
        }
    }

    @Test
    public void getByCategoryId() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.getByCategoryId(3);
        List<PostCategory> help = postCategoryManagerSQL.getByCategoryId(3);
        if(help.get(0).getId()!=postCategory.getId()||help.get(0).getPostId()!=postCategory.getPostId()||help.get(0).getCategoryId()!=postCategory.getCategoryId()){
            assert (0==1);
        }
    }

    @Test
    public void getPostsInCategory() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        List<Post> list = new ArrayList<>();
        Post post = new Post(2,4,"post");
        Post post2 = new Post(4,4,"post");
        list.add(post);
        list.add(post2);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(post.getId()).thenReturn(post2.getId());
        when(resultSet.getLong("userId")).thenReturn(post.getUserId()).thenReturn(post2.getUserId());

        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.getPostsInCategory(3);
        List<Post> help = postCategoryManagerSQL.getPostsInCategory(3);
        for(int i=0; i<2; i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getUserId()!=list.get(i).getUserId()){
                assert(0==1);
            }
        }


    }

    @Test
    public void getCategoriesOfPost() throws SQLException, InterruptedException {
        when(connection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException()).thenReturn(preparedStatement);
        List<Category> list = new ArrayList<>();
        Category category = new Category(3,"interesting","nice");
        Category category2 = new Category(2,"notInteresting","bad");
        list.add(category);
        list.add(category2);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(category.getId()).thenReturn(category2.getId());
        when(resultSet.getString("name")).thenReturn(category.getName()).thenReturn(category2.getName());
        when(resultSet.getString("description")).thenReturn(category.getDescription()).thenReturn(category2.getDescription());

        PostCategoryManagerSQL postCategoryManagerSQL = new PostCategoryManagerSQL(connectionPool);
        postCategoryManagerSQL.getCategoriesOfPost(1);
        List<Category> help = postCategoryManagerSQL.getCategoriesOfPost(1);
        for(int i=0; i<2; i++){
            if(help.get(i).getId()!=list.get(i).getId()||help.get(i).getName()!=list.get(i).getName()||help.get(i).getDescription()!=list.get(i).getDescription()){
                assert(1==2);
            }
        }
    }
}