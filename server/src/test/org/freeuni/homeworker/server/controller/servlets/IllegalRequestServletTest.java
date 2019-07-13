package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IllegalRequestServletTest {

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
    private IllegalRequestServlet illegalRequestServlet;
    private String result;

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        objectMapper = new ObjectMapper();
        illegalRequestServlet = new IllegalRequestServlet();

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(response.getWriter())
                .thenReturn(writer);
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

        illegalRequestServlet.doGet(request, response);
        JsonNode responseNode = objectMapper.readTree(result);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }

    @Test
    public void doPost() throws IOException, ServletException {

        illegalRequestServlet.doPost(request, response);
        JsonNode responseNode = objectMapper.readTree(result);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }
}