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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryCreateModifyServletTest {

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
    @Mock
    private BufferedReader reader;

    private ObjectMapper objectMapper;
    private CategoryCreateModifyServlet categoryCreateModifyServlet;
    private String resultingJSON;
    private Category addedCategory;
    private String inputString;
    private Category modifiedCategory;

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        categoryCreateModifyServlet = new CategoryCreateModifyServlet();
        objectMapper = new ObjectMapper();

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(servletContext.getAttribute(ContextKeys.CATEGORY_MANAGER))
                .thenReturn(categoryManager);

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedCategory = (Category) args[0];
            return null;
        }).when(categoryManager).add(any());

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            modifiedCategory = (Category) args[0];
            return null;
        }).when(categoryManager).modifyCategory(any());

        when(response.getWriter())
                .thenReturn(writer);
        when(request.getReader())
                .thenReturn(reader);

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            resultingJSON = args[0].toString();
            return null;
        }).when(writer).write(anyString());

    }

    @Test
    public void doPost() throws IOException, ServletException {
        inputString = "{ \"name\" : \"test name\", \"description\" : \"test desc\" }";

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        categoryCreateModifyServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("id"));
        assertEquals(responseNode.get("id").asLong(), 0);
        assertEquals(addedCategory.getDescription(), "test desc");
        assertEquals(addedCategory.getName(), "test name");
    }

    @Test
    public void doPostFail() throws IOException, SQLException, InterruptedException, ServletException {
        when(request.getParameter("id"))
                .thenReturn(null);
        when(request.getParameter("name"))
                .thenReturn(null);

        inputString = "{ \"name\" : \"test name\", \"description\" : \"test desc\" }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        when(categoryManager.add(any()))
                .thenThrow(new SQLException());

        categoryCreateModifyServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
        verify(writer)
                .write(anyString());
    }

    @Test
    public void doPut() throws IOException, ServletException {
        inputString = "{ \"id\" : 1," +
                " \"name\" : \"test name\"," +
                " \"description\" : \"test desc\" }";

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        categoryCreateModifyServlet.doPut(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals(responseNode.get("STATUS").asText(), "OK");
        assertEquals(modifiedCategory.getDescription(), "test desc");
        assertEquals(modifiedCategory.getName(), "test name");
        assertEquals(modifiedCategory.getId(), 1L);
    }

    @Test
    public void doPutFail() throws IOException, ServletException {
        inputString = "{ \"id\" : 1," +
                " \"name\" : \"test name\"," +
                " \"description\" : \"test desc\" }";

        when(reader.readLine())
                .thenReturn(null);

        categoryCreateModifyServlet.doPut(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals(responseNode.get("STATUS").asText(), "ERROR");
    }
}