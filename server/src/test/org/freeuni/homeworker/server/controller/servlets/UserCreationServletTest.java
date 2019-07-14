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

@RunWith(MockitoJUnitRunner.class)
public class UserCreationServletTest {
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

    private UserCreationServlet userCreationServlet;

    private ObjectMapper objectMapper;
    private String resultingJSON;
    private String inputString;
    private User addedUser;

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
        userCreationServlet = new UserCreationServlet();
        objectMapper = new ObjectMapper();

        initContextAttributes();



        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            addedUser = (User) args[0];
            return null;
        }).when(userManager).addUser(any());


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
    public void doPost() throws IOException, SQLException, InterruptedException {
        inputString = " { \"firstName\" : \"Jon\", " +
                "\"lastName\" : \"Jon\", " +
                "\"gender\" : \"elk\"," +
                "\"email\" : \"Jon\",  " +
                "\"password\" : \"Jon\"}  ";
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

        userCreationServlet.doPost(request, response);

        verify(userManager,times(1))
                .addUser(any());

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals("Jon", addedUser.getEmail());
        assertEquals("OK", responseNode.get("STATUS").asText());
//        assertFalse(responseNode.get("isValid").asBoolean());
    }

    @Test
    public void doPostFail() throws IOException, SQLException, InterruptedException {
        inputString = " { \"firstName\" 1231 a; adqweqe ";
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

        userCreationServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        //assertEquals("Jon", addedUser.getEmail());
        assertEquals("ERROR", responseNode.get("STATUS").asText());
//        assertFalse(responseNode.get("isValid").asBoolean());
    }

}