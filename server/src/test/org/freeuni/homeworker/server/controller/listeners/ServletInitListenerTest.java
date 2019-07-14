package org.freeuni.homeworker.server.controller.listeners;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.lang.reflect.Field;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServletInitListenerTest {
    @Mock
    ServletContextEvent event;
    @Mock
    ServletContext context;
    @Mock
    GeneralManagerSQL manager;

    ServletInitListener listener;

    @Test
    public void contextInitialized() {
        //ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(Obbje.class);
        when(event.getServletContext())
                .thenReturn(context);

        listener = new ServletInitListener();
        listener.contextInitialized(event);

        for(Field field : ManagerNameKeys.class.getFields()) {
            try {
                verify(context).setAttribute(eq((String)field.get(ManagerNameKeys.class)), anyObject());
            } catch (IllegalAccessException e) { e.printStackTrace(); }
        }

    }

    @Test
    public void contextDestroyed() {
        when(event.getServletContext())
                .thenReturn(context);
        when(context.getAttribute(anyString()))
                .thenReturn(manager);
        listener = new ServletInitListener();
        listener.contextDestroyed(event);
        verify(manager,atLeast(ManagerNameKeys.class.getFields().length))
                    .destroyManager();
        verify(context,atLeast(ManagerNameKeys.class.getFields().length))
                .getAttribute(anyString());
    }

}