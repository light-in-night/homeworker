package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManager;
import org.freeuni.homeworker.server.model.managers.posts.PostManager;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostAccessServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Session session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private BufferedReader reader;
    @Mock
    private PrintWriter writer;
    @Mock
    private Session testSession;
    @Mock
    UserManager userManager;
    @Mock
    PostManager postManager;
    @Mock
    PostCategoryManager postCategoryManager;
    @Mock
    private PostCategory testPostCategory;
    @Mock
    private User testUser;

    private ObjectMapper objectMapper;
    private PostAccessServlet postAccessServlet;
    private String testEmail = "testemail";
    private String testPassword = "testpass";
    private String testSessionId;
    private String inputJSON;
    private String resultingJSON;
    List<Post> postList;
    Post post0,post1;
    private long testCategoryId;

    @Before
    public void setUp() throws Exception {
        testSessionId = "test";

        objectMapper = new ObjectMapper();
        postAccessServlet = new PostAccessServlet();

        when(request.getServletContext())
                .thenReturn(servletContext);
        when(servletContext.getAttribute(ContextKeys.OBJECT_MAPPER))
                .thenReturn(objectMapper);
        when(response.getWriter())
                .thenReturn(writer);
        when(servletContext.getAttribute(ContextKeys.SESSION_MANAGER))
                .thenReturn(sessionManager);
        when(request.getHeader(ContextKeys.SESSION_ID))
                .thenReturn(testSessionId);
        when(sessionManager.getSession(testSessionId))
                .thenReturn(testSession);
        when(servletContext.getAttribute(ContextKeys.USER_MANAGER))
                .thenReturn(userManager);
        when(userManager.getUserByEmail(anyString()))
                .thenReturn(testUser);
        when(servletContext.getAttribute(ContextKeys.POST_MANAGER))
                .thenReturn(postManager);

        when(postCategoryManager.getByPostId(anyInt()))
                .thenReturn(testPostCategory);


        when(testUser.getEmail())
                .thenReturn(testEmail);
        when(testUser.getPassword())
                .thenReturn(testPassword);
        when(testUser.getId())
                .thenReturn(1L);

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
    public void doGet() throws IOException, SQLException, InterruptedException {
        when(request.getParameter(anyString()))
                .thenReturn(null);
//        when(request.getParameter("categoryId"))
//                .thenReturn("1");
        post0 = new Post(0, 1, "test",new Timestamp(123L));
        post1 = new Post(1, 2, "test2", new Timestamp(123L));
        postList = Arrays.asList(
                post0,post1
        );
        when(postManager.getPosts(any(),any(),any()))
                .thenReturn(postList);
        testCategoryId = 1L;
        when(testPostCategory.getCategoryId())
                .thenReturn(testCategoryId);

        postAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertEquals(2, responseNode
                .get("posts")
                .size());
        assertEquals(0, responseNode
                .get("posts").get(0).get("id").asLong());
        assertEquals("test2", responseNode
                .get("posts").get(1).get("contents").asText());
    }


    @Test
    public void doGetFail()  throws IOException, SQLException, InterruptedException {
        when(request.getParameter(anyString()))
                .thenReturn(null);
        when(request.getParameter("categoryId"))
                .thenReturn("abc");

        post0 = new Post(0, 1, "test",new Timestamp(123L));
        post1 = new Post(1, 2, "test2", new Timestamp(123L));
        postList = Arrays.asList(
                post0,post1
        );
        when(postManager.getPosts(any(),any(),any()))
                .thenReturn(null);
        testCategoryId = 1L;
        when(testPostCategory.getCategoryId())
                .thenReturn(testCategoryId);

        postAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());

    }

    @Test
    public void doGetOK() throws IOException, SQLException, InterruptedException {
        when(request.getParameter(anyString()))
                .thenReturn("asd");

        post0 = new Post(0, 1, "test",new Timestamp(123L));
        post1 = new Post(1, 2, "test2", new Timestamp(123L));
        postList = Arrays.asList(
                post0,post1
        );
        when(postManager.getPosts(any(),any(),any()))
                .thenReturn(postList);
        testCategoryId = 1L;
        when(testPostCategory.getCategoryId())
                .thenReturn(testCategoryId);

        postAccessServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertEquals(2, responseNode
                .get("posts")
                .size());
        assertEquals("test2", responseNode
                .get("posts")
                .get(1)
                .get("contents")
                .asText());
    }
}