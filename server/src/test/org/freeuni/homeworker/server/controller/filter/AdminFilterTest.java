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
public class AdminFilterTest {
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

    String adminPassword = "abc";
    String wrongPass = "abc";
    AdminFilter adminFilter;

    @Before
    public void setUp() throws Exception {
        when(request.getServletContext())
                .thenReturn(context);
        when(context.getAttribute(eq(ContextKeys.SESSION_MANAGER)))
                .thenReturn(sessionManager);
        when(context.getAttribute(eq(ContextKeys.OBJECT_MAPPER)))
                .thenReturn(mapper);
        when(request.getRequestDispatcher(anyString()))
                .thenReturn(requestDispatcher);
        adminFilter = new AdminFilter();
    }

    @Test
    public void doFilter() throws IOException, ServletException {
        when(request.getHeader(eq("adminPassword")))
                .thenReturn(adminPassword)
                .thenReturn(adminPassword);
        adminFilter.doFilter(request, response, chain);
        verify(chain)
                .doFilter(request, response);



        when(request.getHeader(eq("adminPassword")))
                .thenReturn(null)
                .thenReturn(null);
        adminFilter.doFilter(request, response, chain);
        verify(request)
                .getRequestDispatcher(anyString());

    }

    @Test
    public void destroy() {
        adminFilter.destroy();
    }
}