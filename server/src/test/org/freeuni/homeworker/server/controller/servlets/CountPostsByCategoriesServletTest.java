package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountPostsByCategoriesServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private PostCategoryManager postCategoryManager;
    @Mock
    private ServletContext servletContext;
    @Mock
    private PrintWriter writer;

    private Category cat1,cat2;
    private ObjectMapper objectMapper;
    private List<Category> categoryList;
    private CountPostsByCategoriesServlet countPostsByCategoriesServlet;
    private String result;

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        objectMapper = new ObjectMapper();
        countPostsByCategoriesServlet = new CountPostsByCategoriesServlet();

        cat1 = new Category(1, "cat1", "cat1 desc");
        cat2 = new Category(2, "cat2", "cat2 desc");

        categoryList = new ArrayList<>(Arrays.asList(
                cat1,cat2
        ));

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(servletContext.getAttribute(ContextKeys.CATEGORY_MANAGER))
                .thenReturn(categoryManager);
        when(response.getWriter())
                .thenReturn(writer);
        when(servletContext.getAttribute(ContextKeys.POST_CATEGORY_MANAGER))
                .thenReturn(postCategoryManager);
        when(categoryManager.getAllCategories())
                .thenReturn(categoryList);
        when(postCategoryManager.getPostsInCategory(anyLong()))
                .thenReturn(Arrays.asList(new Post(),new Post()));


        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            result = args[0].toString();
            return null;
        }).when(writer).write(anyString());

        when(categoryManager.getAllCategories())
                .thenReturn(categoryList);
    }

    @Test
    public void doGet() throws IOException, ServletException {

        countPostsByCategoriesServlet.doGet(request, response);
        JsonNode responseNode = objectMapper.readTree(result);

        assertTrue(responseNode.has("categories"));
        assertEquals(2, responseNode
                .get("categories").size());
        assertTrue(responseNode
                .get("categories")
                .get(0).has("id"));
        assertEquals(2L, responseNode.get("categories")
                .get(0)
                .get("postCount").asLong());
        assertEquals(2, responseNode
                .get("categories")
                .size());

        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }

    @Test
    public void doGetFail() throws IOException, SQLException, InterruptedException, ServletException {
        when(request.getParameter("id"))
                .thenReturn(null);
        when(request.getParameter("name"))
                .thenReturn(null);

        when(categoryManager.getAllCategories())
                .thenThrow(new SQLException());
        countPostsByCategoriesServlet.doGet(request, response);
        JsonNode responseNode = objectMapper.readTree(result);


        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }
}