package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.freeuni.homeworker.server.model.managers.comments.CommentManager;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.comment.Comment;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentCreateServletTest {

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
    @Mock
    private CommentManager commentManager;

    private CommentCreateServlet commentCreateServlet;

    private ObjectMapper objectMapper;
    private String resultingJSON;
    private String inputString;
    private User updatedUser;

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
        when(servletContext.getAttribute(ContextKeys.COMMENT_MANAGER))
                .thenReturn(commentManager);
    }

    @Before
    public void setUp() throws SQLException, InterruptedException, IOException {
        commentCreateServlet = new CommentCreateServlet();
        objectMapper = new ObjectMapper();

        initContextAttributes();


        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            updatedUser = (User) args[0];
            return null;
        }).when(userManager).updateUser(any());

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
    public void doPost() throws IOException, SQLException, InterruptedException, ServletException {
        inputString = " { " +
                "\"comment\" : " +
                    "{" +
                    "\"id\" : 1 ," +
                    "\"userId\" : 1 ," +
                    "\"postId\" : 1 ," +
                    "\"contents\" : \"test\" " +
                    "}" +
                " } ";

        long userId = 1L;

        setSessionParams(userId,
                "testId",
                true,
                true);

        when(request.getParameter("postId"))
                .thenReturn("1");
        when(request.getParameter("numComments"))
                .thenReturn("2");

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        List<Comment> commentList = Arrays.asList(
                new Comment(1, 1, 1, "test1"),
                new Comment(1, 1, 1, "test2")
        );

        when(commentManager.getCommentsByPost(anyLong(), anyLong()))
                .thenReturn(commentList);
        when(commentManager.add(any()))
                .thenReturn(10L);

        commentCreateServlet.doPost(request, response);


        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertEquals(10L, responseNode
                .get("id")
                .asLong());
        //        assertFalse(responseNode.get("isValid").asBoolean());
    }

    @Test
    public void doPostFail() throws IOException, SQLException, InterruptedException, ServletException {
        inputString = " { " +
                "\"comment\" : " +
                "{" +
                "\"id\" : 1 ," +
                "\"userId\" : 1 ," +
                "\"postId\" : 1 ," +
                "\"contents\" : \"test\" " +
                "}" +
                " } ";

        long userId = 1L;

        setSessionParams(userId,
                "testId",
                true,
                true);

        when(request.getParameter("postId"))
                .thenReturn("1");
        when(request.getParameter("numComments"))
                .thenReturn("2");

        when(reader.readLine())
                .thenReturn(inputString)
                .thenReturn(null);

        List<Comment> commentList = Arrays.asList(
                new Comment(1, 1, 1, "test1"),
                new Comment(1, 1, 1, "test2")
        );

        when(commentManager.getCommentsByPost(anyLong(), anyLong()))
                .thenThrow(new SQLException());
        when(commentManager.add(any()))
                .thenThrow(new SQLException());

        commentCreateServlet.doPost(request, response);


        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }

}