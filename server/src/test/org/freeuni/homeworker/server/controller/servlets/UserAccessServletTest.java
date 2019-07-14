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
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.session.Session;
import org.freeuni.homeworker.server.model.objects.user.User;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAccessServletTest {
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
    @Mock
    UserManager userManager;

    private ObjectMapper objectMapper;
    private UserAccessServlet userAccessServlet;
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
        when(servletContext.getAttribute(ContextKeys.USER_MANAGER))
                .thenReturn(userManager);
    }

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        userAccessServlet = new UserAccessServlet();
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
        User user1 = new User(1L, "test1", "", "", "", "");
        User user2 = new User(2L, "test2", "", "", "", "");

        when(request.getParameter(ContextKeys.SESSION_ID))
                .thenReturn(testSessionId);
        when(request.getParameter(anyString()))
                .thenReturn(null);
        when(userManager.getUsers(any()))
                .thenReturn(Arrays.asList(user1,user2));

        setSessionParams(userId, testSessionId, true, true);

        userAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals(2, responseNode
                .get("users")
                .size());
        assertEquals("test2", responseNode
                .get("users")
                .get(1)
                .get("firstName")
                .asText());
    }

    @Test
    public void doGetFail() throws SQLException, InterruptedException, ServletException, IOException {
        long userId = 1L;
        String testSessionId = "123";
        when(request.getParameter(ContextKeys.SESSION_ID))
                .thenReturn(null);

        setSessionParams(userId, testSessionId, true, true);
        when(userManager.getUsers(any()))
                .thenThrow(new SQLException());
        userAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals("ERROR", responseNode.get("STATUS").asText());
//        assertFalse(responseNode.get("isValid").asBoolean());
    }

}