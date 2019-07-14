package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
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
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostLikeServletTest {
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
    @Mock
    PostLikeManager postLikeManager;
    @Mock
    SessionManager sessionManager;
    @Mock
    private Session testSession;

    private ObjectMapper objectMapper;
    private PostLikeServlet postLikeServlet;
    private String resultingJSON;
    private Category addedCategory;
    private String inputString;
    private Category modifiedCategory;
    private PostLike addedPostLikeNext;
    private PostLike addedPostLikeFirst;
    private PostLike postLike1,postLike2;

    private void initContextAttributes() {
        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(servletContext.getAttribute(ContextKeys.CATEGORY_MANAGER))
                .thenReturn(categoryManager);
        when(servletContext.getAttribute(ContextKeys.POST_LIKE_MANAGER))
                .thenReturn(postLikeManager);
        when(servletContext.getAttribute(ContextKeys.SESSION_MANAGER))
                .thenReturn(sessionManager);
    }

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        postLikeServlet = new PostLikeServlet();
        objectMapper = new ObjectMapper();

        initContextAttributes();

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedPostLikeFirst = (PostLike) args[0];
            return null;
        }).when(postLikeManager).rateFirstTime(any());

        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedPostLikeNext = (PostLike) args[0];
            return null;
        }).when(postLikeManager).rateNextTime(any());

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
    public void doGet() throws SQLException, InterruptedException, ServletException, IOException {
        postLike1 = new PostLike(1L, 1L, 1L, true);
        postLike2 = new PostLike(2L, 2L, 2L, false);

        when(request.getParameter("postId"))
                .thenReturn("1");
        when(postLikeManager.getByPost(1L))
                .thenReturn(Arrays.asList(postLike1,postLike2));

        postLikeServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("users"));
        assertEquals(1, responseNode
                .get("users")
                .get("liked")
                .get(0)
                .asLong());
        assertEquals(2, responseNode
                .get("users")
                .get("disliked")
                .get(0)
                .asLong());
    }


    @Test
    public void doGetFail() throws SQLException, InterruptedException, ServletException, IOException {
        postLike1 = new PostLike(1L, 1L, 1L, true);
        postLike2 = new PostLike(2L, 2L, 2L, false);

        when(request.getParameter("postId"))
                .thenReturn("1");
        when(postLikeManager.getByPost(1L))
                .thenReturn(null);

        postLikeServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }


    @Test
    public void doPostLikeFirstTime() throws IOException, ServletException, SQLException, InterruptedException {
        inputString = "{ \"postId\" : 1}";

        setSessionParams(1L, "testId", true);

        when(postLikeManager.getByUserAndPost(1L, 123))
                .thenReturn(null);

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        postLikeServlet.doPost(request, response);

        verify(postLikeManager)
                .rateFirstTime(anyObject());

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals(responseNode.get("STATUS").asText(), "OK");
    }

    private void setSessionParams(long testUserId, String testSessionId, boolean isLoggedIn) {
        when(request.getHeader(ContextKeys.SESSION_ID))
                .thenReturn(testSessionId);
        when(sessionManager.getSession(testSessionId))
                .thenReturn(testSession);
        when(testSession.getUserId())
                .thenReturn(testUserId);
        when(testSession.isLoggedIn())
                .thenReturn(isLoggedIn);
    }


    @Test
    public void doPostLikeNextTime() throws IOException, ServletException, SQLException, InterruptedException {
        inputString = "{ \"postId\" : 123}";
        postLike1 = new PostLike(1L, 1L, 1L, true);

        setSessionParams(1L, "testId", true);


        when(postLikeManager.getByUserAndPost(1L, 123))
                .thenReturn(postLike1);

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        postLikeServlet.doPost(request, response);

        verify(postLikeManager)
                .rateNextTime(anyObject());

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertEquals(responseNode.get("STATUS").asText(), "OK");
    }

    @Test
    public void doPostLikeFail() throws IOException, ServletException, SQLException, InterruptedException {
        inputString = "{ \"postId\" : 123}";

        setSessionParams(1L, "testId", true);

        when(postLikeManager.getByUserAndPost(1L, 123))
                .thenThrow(new SQLException());

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        postLikeServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);

        assertEquals(responseNode.get("STATUS").asText(), "ERROR");
    }

}