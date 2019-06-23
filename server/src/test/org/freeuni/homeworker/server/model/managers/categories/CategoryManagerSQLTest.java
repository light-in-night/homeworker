package org.freeuni.homeworker.server.model.managers.categories;


import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManagerSQL;
import org.freeuni.homeworker.server.model.objects.category.Category;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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

    private Category category;
    @Before
    public void setUp() throws SQLException, InterruptedException {
        when(connectionPool.acquireConnection()).thenReturn(connection);

        category = new Category(1,"inter","good");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong("id")).thenReturn(category.getId());
        when(resultSet.getString("name")).thenReturn(category.getName());
        when(resultSet.getString("description")).thenReturn(category.getDescription());
    }

    @Test
    public void add() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        CategoryManagerSQL categoryManagerSQL = new CategoryManagerSQL(connectionPool);
        categoryManagerSQL.add(category);
        categoryManagerSQL.add(category);
    }

    @Test
    public void removeById() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        CategoryManagerSQL categoryManagerSQL = new CategoryManagerSQL(connectionPool);
        categoryManagerSQL.removeById(1);
        categoryManagerSQL.removeById(1);
    }
    @Test
    public void getById() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        CategoryManagerSQL categoryManagerSQL = new CategoryManagerSQL(connectionPool);
        categoryManagerSQL.getById(1);
        Category help = categoryManagerSQL.getById(1);
        if(help.getId()!=category.getId()||help.getName()!=category.getName()||help.getDescription()!=category.getDescription()){
            assert(1==2);
        }
    }
}