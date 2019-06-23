package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryAccessServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private ServletContext servletContext;

    @Before
    public void setUp(){
        when(request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER)).thenReturn(servletContext);
        when(request.getServletContext().getAttribute(ContextKeys.CATEGORY_MANAGER)).thenReturn(objectMapper);

    }

    @Test
    public void doGet() throws IOException {
        CategoryAccessServlet categoryAccessServlet = new CategoryAccessServlet();
        categoryAccessServlet.doGet(request,response);
    }
}