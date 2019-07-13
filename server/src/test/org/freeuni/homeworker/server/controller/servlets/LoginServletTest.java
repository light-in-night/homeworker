package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

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
    private User testUser;


    private ObjectMapper objectMapper;
    private LoginServlet loginServlet;
    private String testEmail = "testemail";
    private String testPassword = "testpass";
    private String testSessionId;
    private String inputJSON;
    private String resultingJSON;

    @Before
    public void setUp() throws Exception {
        testSessionId = "test";

        objectMapper = new ObjectMapper();
        loginServlet = new LoginServlet();

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
    public void doGetTrue() throws ServletException, IOException {
        when(testSession.isLoggedIn())
                .thenReturn(true);

        loginServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertTrue(responseNode
                .get("loggedIn")
                .asBoolean());
    }

    @Test
    public void doGetFalse() throws ServletException, IOException {
        when(testSession.isLoggedIn())
                .thenReturn(false);

        loginServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
        assertFalse(responseNode
                .get("loggedIn")
                .asBoolean());
    }


    @Test
    public void doGetFail() throws ServletException, IOException {
        when(testSession.isLoggedIn())
                .thenReturn(false);

        when(sessionManager.getSession(anyString()))
                .thenReturn(null);
        loginServlet.doGet(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPostGoodLogin() throws ServletException, IOException {
        inputJSON = "{ \"email\" : \""+testEmail+"\", \"password\" : \""+testPassword+"\" }";

        when(reader.readLine())
                .thenReturn(inputJSON)
                .thenReturn(null);

        when(testUser.getEmail())
                .thenReturn(testEmail);
        when(testUser.getPassword())
                .thenReturn(testPassword);
        when(testUser.getId())
                .thenReturn(1L);

        loginServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("OK", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPostBadLogin() throws ServletException, IOException {
        inputJSON = "{ \"email\" : \""+testEmail+"\", \"password\" : \""+testPassword+"\" }";

        when(reader.readLine())
                .thenReturn(inputJSON)
                .thenReturn(null);

        when(testUser.getEmail())
                .thenReturn(testEmail);
        when(testUser.getPassword())
                .thenReturn(testPassword + "a");
        when(testUser.getId())
                .thenReturn(1L);

        loginServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }

    @Test
    public void doPostFail() throws ServletException, IOException, SQLException, InterruptedException {
        inputJSON = "{ \"email\" : \""+testEmail+"\", \"password\" : \""+testPassword+"\" }";

        when(reader.readLine())
                .thenReturn(inputJSON)
                .thenReturn(null);

        when(testUser.getEmail())
                .thenReturn(testEmail);
        when(testUser.getPassword())
                .thenReturn(testPassword + "a");
        when(testUser.getId())
                .thenReturn(1L);

        when(userManager.getUserByEmail(any()))
                .thenReturn(null);

        loginServlet.doPost(request, response);

        JsonNode responseNode = objectMapper.readTree(resultingJSON);
        assertTrue(responseNode.has("STATUS"));
        assertEquals("ERROR", responseNode.get("STATUS").asText());
    }
}