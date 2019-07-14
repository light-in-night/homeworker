package org.freeuni.homeworker.server.model.managers.categories;


import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManagerSQL;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryManagerSQLTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    private CategoryManagerSQL categoryManagerSQL;

    private Category category,nextCategory;

    @Before
    public void setUp() throws SQLException, InterruptedException {
        category = new Category(1,"test category","test desc");
        nextCategory = new Category(1,"next test category","next test desc");

        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class)))
                .thenReturn(preparedStatement);
        when(connection.prepareStatement(any(String.class),anyInt()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong("id"))
                .thenReturn(category.getId())
                .thenReturn(nextCategory.getId());
        when(resultSet.getString("name"))
                .thenReturn(category.getName())
                .thenReturn(nextCategory.getName());
        when(resultSet.getString("description"))
                .thenReturn(category.getDescription())
                .thenReturn(nextCategory.getDescription());
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        categoryManagerSQL = new CategoryManagerSQL(connectionPool);
    }

    public void faultyStart() throws SQLException {
        when(connection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException())
                .thenReturn(preparedStatement);
        when(connection.prepareStatement(any(String.class),anyInt()))
                .thenThrow(new SQLException())
                .thenReturn(preparedStatement);
    }

    @Test
    public void add() throws SQLException, InterruptedException {
        ResultSet genKeyResSet = Mockito.mock(ResultSet.class);
        when(preparedStatement.getGeneratedKeys())
                .thenReturn(genKeyResSet);
        when(genKeyResSet.next())
                .thenReturn(true);

        categoryManagerSQL.add(category);
    }

    @Test(expected = SQLException.class)
    public void addWithException1() throws SQLException, InterruptedException {

        ResultSet genKeyResSet = Mockito.mock(ResultSet.class);
        when(preparedStatement.getGeneratedKeys())
                .thenReturn(genKeyResSet);
        when(genKeyResSet.next())
                .thenReturn(false);
        categoryManagerSQL.add(category);
    }

    @Test(expected = SQLException.class)
    public void addWithException() throws SQLException, InterruptedException {
        faultyStart();
        categoryManagerSQL.add(category);
        categoryManagerSQL.add(category);
    }

    @Test
    public void removeById() throws SQLException, InterruptedException {
        categoryManagerSQL.removeById(1);
        categoryManagerSQL.removeById(1);
    }

    @Test(expected = SQLException.class)
    public void removeByIdWithException() throws SQLException, InterruptedException {
        faultyStart();
        categoryManagerSQL.removeById(1);
        categoryManagerSQL.removeById(1);
    }

    @Test
    public void getById() throws SQLException, InterruptedException {
        Category testcat = categoryManagerSQL.getById(1);
        assertEquals(testcat.getName(), category.getName());
        assertEquals(testcat.getId(), category.getId());
        assertEquals(testcat.getDescription(), category.getDescription());
    }

    @Test(expected = SQLException.class)
    public void getByIdWithException() throws SQLException, InterruptedException {
        faultyStart();
        categoryManagerSQL.getById(1);
        categoryManagerSQL.getById(1);
        Category testcat = categoryManagerSQL.getById(1);
        assertEquals(testcat.getName(), category.getName());
        assertEquals(testcat.getId(), category.getId());
        assertEquals(testcat.getDescription(), category.getDescription());
    }
    @Test
    public void getAllCategories() throws SQLException, InterruptedException {
        List<Category> catList = categoryManagerSQL.getAllCategories();

        assertEquals(catList.get(0).getName(), category.getName());
        assertEquals(catList.get(0).getId(), category.getId());
        assertEquals(catList.get(0).getDescription(), category.getDescription());

        assertEquals(catList.get(1).getName(), nextCategory.getName());
        assertEquals(catList.get(1).getId(), nextCategory.getId());
        assertEquals(catList.get(1).getDescription(), nextCategory.getDescription());
    }

    @Test(expected = SQLException.class)
    public void getAllCategoriesWithException() throws SQLException, InterruptedException {
        faultyStart();
        List<Category> catList = categoryManagerSQL.getAllCategories();
        catList = categoryManagerSQL.getAllCategories();

        assertEquals(catList.get(0).getName(), category.getName());
        assertEquals(catList.get(0).getId(), category.getId());
        assertEquals(catList.get(0).getDescription(), category.getDescription());

        assertEquals(catList.get(1).getName(), nextCategory.getName());
        assertEquals(catList.get(1).getId(), nextCategory.getId());
        assertEquals(catList.get(1).getDescription(), nextCategory.getDescription());
    }
    @Test
    public void modifyCategory() throws SQLException, InterruptedException {

        categoryManagerSQL.modifyCategory(category);

        verify(preparedStatement).setLong(anyInt(), eq(category.getId()));
        verify(preparedStatement).setString(anyInt(), eq(category.getDescription()));
        verify(preparedStatement).setString(anyInt(), eq(category.getName()));
        verify(preparedStatement).executeUpdate();

    }

    @Test(expected = SQLException.class)
    public void modifyCategoryWithException() throws SQLException, InterruptedException {
        faultyStart();
        categoryManagerSQL.modifyCategory(category);
        categoryManagerSQL.modifyCategory(category);
        verify(preparedStatement).setLong(anyInt(), eq(category.getId()));
        verify(preparedStatement).setString(anyInt(), eq(category.getDescription()));
        verify(preparedStatement).setString(anyInt(), eq(category.getName()));
        verify(preparedStatement).executeUpdate();
    }
}