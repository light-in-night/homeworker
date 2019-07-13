package org.freeuni.homeworker.server.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginFilterTest {

        @Mock
        HttpServletResponse response;
        @Mock
        HttpServletRequest request;
        @Mock
        ServletContext context;
        @Mock
        SessionManager sessionManager;
        @Mock
        ObjectMapper mapper;
        @Mock
        RequestDispatcher requestDispatcher;
        @Mock
        FilterChain chain;

        String testSessionId;
        LoginFilter loginFilter;

        @Before
        public void setUp() throws Exception {
            testSessionId = "test";
            loginFilter = new LoginFilter();

            when(request.getServletContext())
                    .thenReturn(context);
            when(context.getAttribute(eq(ContextKeys.SESSION_MANAGER)))
                    .thenReturn(sessionManager);
            when(context.getAttribute(eq(ContextKeys.OBJECT_MAPPER)))
                    .thenReturn(mapper);
            when(request.getRequestDispatcher(anyString()))
                    .thenReturn(requestDispatcher);
            when(request.getHeader(anyString()))
                    .thenReturn(null)
                    .thenReturn(testSessionId);
            when(sessionManager.isUserLoggedIn(testSessionId))
                    .thenReturn(true);
        }

        @Test
        public void doFilter() throws IOException, ServletException {
            loginFilter.doFilter(request, response, chain);
            verify(request)
                    .getRequestDispatcher(anyString());
            loginFilter.doFilter(request, response, chain);
            verify(chain)
                    .doFilter(request, response);
        }

        @Test
        public void destroy() {
            loginFilter.destroy();
        }
    }