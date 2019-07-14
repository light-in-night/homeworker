package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
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
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionCreationServletTest {
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
    @Mock
    PostCategoryManager postCategoryManager;
    @Mock
    PostManager postManager;

    private ObjectMapper objectMapper;
    private SessionCreationServlet sessionCreationServlet;
    private String resultingJSON;
    private Category addedCategory;
    private String inputString;
    private Category modifiedCategory;
    private PostLike addedPostLikeNext;
    private PostLike addedPostLikeFirst;
    private PostLike postLike1,postLike2;

    private void setSessionParams(long testUserId, String testSessionId, boolean isLoggedIn, boolean hasSession) {
        when(request.getHeader(ContextKeys.SESSION_ID))
                .thenReturn(testSessionId);
        when(sessionManager.getSession(testSessionId))
                .thenReturn(testSession);
        when(sessionManager.hasSession(testSessionId))
                .thenReturn(hasSession);
        when(testSession.getUserId())
                .thenReturn(testUserId);
        when(testSession.isLoggedIn())
                .thenReturn(isLoggedIn);
    }

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
        when(servletContext.getAttribute(ContextKeys.POST_CATEGORY_MANAGER))
                .thenReturn(postCategoryManager);
        when(servletContext.getAttribute(ContextKeys.POST_MANAGER))
                .thenReturn(postManager);
    }

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        sessionCreationServlet = new SessionCreationServlet();
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
    public void doGetIsValid() throws SQLException, InterruptedException, ServletException, IOException {
        long userId = 1L;
        String testSessionId = "123";
        when(request.getParameter(ContextKeys.SESSION_ID))
                .thenReturn(testSessionId);

        setSessionParams(userId, testSessionId, true, true);

        sessionCreationServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.get("isValid").asBoolean());
    }

    @Test
    public void doGetIsNotValid() throws SQLException, InterruptedException, ServletException, IOException {
        long userId = 1L;
        String testSessionId = "123";
        when(request.getParameter(ContextKeys.SESSION_ID))
                .thenReturn(null);

        setSessionParams(userId, testSessionId, true, true);

        sessionCreationServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertFalse(responseNode.get("isValid").asBoolean());
    }

    @Test
    public void doPost() throws IOException, ServletException, SQLException, InterruptedException {
        inputString = "";
        long userId = 1L;

        setSessionParams(userId,
                "testId",
                true,
                true);

        when(sessionManager.createNewSession())
                .thenReturn("abcd");
        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        sessionCreationServlet.doPost(request, response);


        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals(responseNode.get("STATUS").asText(), "OK");
        assertEquals(responseNode.get("sessionId").asText(), "abcd");
    }

}