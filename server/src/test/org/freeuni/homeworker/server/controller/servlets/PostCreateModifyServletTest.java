package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManagerSQLTest;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
import org.freeuni.homeworker.server.model.objects.session.Session;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostCreateModifyServletTest {

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
    private PostManager postManager;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Session testSession;
    @Mock
    private PrintWriter writer;
    @Mock
    private BufferedReader reader;
    @Mock
    private Post testPost;

    private ObjectMapper objectMapper;
    private String resultingJSON;
    private String inputString;
    private Post addedPost;
    private long testUserId;
    private List<PostCategory> addedPostCategoryList;

    private PostCreateModifyServlet postCreateModifyServlet;

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        addedPostCategoryList = new ArrayList<>();
        postCreateModifyServlet = new PostCreateModifyServlet();
        objectMapper = new ObjectMapper();

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(servletContext.getAttribute(ContextKeys.POST_MANAGER))
                .thenReturn(postManager);
        when(servletContext.getAttribute(ContextKeys.CATEGORY_MANAGER))
                .thenReturn(categoryManager);
        when(servletContext.getAttribute(ContextKeys.POST_CATEGORY_MANAGER))
                .thenReturn(postCategoryManager);
        when(servletContext.getAttribute(ContextKeys.SESSION_MANAGER))
                .thenReturn(sessionManager);
        when(request.getHeader(ContextKeys.SESSION_ID))
                .thenReturn("test");
        when(sessionManager.getSession(anyString()))
                .thenReturn(testSession);


        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedPostCategoryList.add((PostCategory) args[0]);
            return null;
        }).when(postCategoryManager).add(any());

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedPost = (Post) args[0];
            return null;
        }).when(postManager).add(any());

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
        inputString = "{ \"post\" : { \"contents\" : \"test post\" }, " +
                "\"categories\" : [1,2] }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        testUserId = 1L;
        when(testSession.getUserId())
                .thenReturn(testUserId);

        postCreateModifyServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertEquals(responseNode
        .get("id").asLong(), 0);
    }

    @Test
    public void doPostFail() throws IOException, SQLException, InterruptedException, ServletException {
        inputString = "{ \"post\" : { \"contents\" : \"test post\" }, " +
                "\"categories\" : [1,2] }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        testUserId = 1L;
        when(testSession.getUserId())
                .thenReturn(testUserId);
        when(postManager.add(any()))
                .thenThrow(new SQLException());

        postCreateModifyServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPutGoodLogin() throws IOException, ServletException, SQLException, InterruptedException {
        testUserId = 1L;
        inputString = "{ \"id\" : 1, " +
                "\"contents\" : \"test contents\" }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        when(testSession.getUserId())
                .thenReturn(testUserId);

        when(postManager.getById(anyLong()))
                .thenReturn(testPost);

        when(testPost.getUserId())
                .thenReturn(1L);

        postCreateModifyServlet.doPut(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPutBadLogin() throws IOException, ServletException, SQLException, InterruptedException {
        testUserId = 1L;
        inputString = "{ \"id\" : 1, " +
                "\"contents\" : \"test contents\" }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        when(testSession.getUserId())
                .thenReturn(testUserId);

        when(postManager.getById(anyLong()))
                .thenReturn(testPost);

        when(testPost.getUserId())
                .thenReturn(2L);

        postCreateModifyServlet.doPut(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPutFail() throws IOException, ServletException, SQLException, InterruptedException {
        testUserId = 1L;
        inputString = "{ \"id\" : 1, " +
                "\"contents\" : \"test contents\" }";
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);
        when(testSession.getUserId())
                .thenReturn(testUserId);

        when(postManager.getById(anyLong()))
                .thenReturn(null);

        when(testPost.getUserId())
                .thenReturn(2L);

        postCreateModifyServlet.doPut(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }
}