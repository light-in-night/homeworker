package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryAccessServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private ServletContext servletContext;

    @Mock
    private Stream<Category> stream;
    @Mock
    private ArrayNode arrayNode;
    @Mock
    private PrintWriter writer;

    private Category cat1,cat2;
    private ObjectMapper objectMapper;
    private List<Category> categoryList;
    private CategoryAccessServlet categoryAccessServlet;
    private String result;

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        categoryAccessServlet = new CategoryAccessServlet();

        cat1 = new Category(1, "cat1", "cat1 desc");
        cat2 = new Category(2, "cat2", "cat2 desc");

        categoryList = new ArrayList<>(Arrays.asList(
                cat1,cat2
        ));
        objectMapper = new ObjectMapper();

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(servletContext.getAttribute(ContextKeys.CATEGORY_MANAGER))
                .thenReturn(categoryManager);
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
    public void doGet() throws IOException {
        when(request.getParameter("id"))
                .thenReturn(null);
        when(request.getParameter("name"))
                .thenReturn(null);
        //TODO Fails
        categoryAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(result);

        assertTrue(responseNode.has("categories"));
        assertEquals(2, responseNode.get("categories").size());
        assertTrue(responseNode.get("categories").get(0).has("id"));
        assertEquals(responseNode.get("categories").get(0).get("id").asLong(), cat1.getId());
        assertEquals(responseNode.get("categories").get(1).get("id").asLong(), cat2.getId());
        verify(writer)
                .write(anyString());
    }

    @Test
    public void doGetFail() throws IOException, SQLException, InterruptedException {
        when(request.getParameter("id"))
                .thenReturn(null);
        when(request.getParameter("name"))
                .thenReturn(null);

        when(categoryManager.getAllCategories())
                .thenThrow(new SQLException());
        categoryAccessServlet.doGet(request, response);
        JsonNode responseNode = objectMapper.readTree(result);


        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }
}