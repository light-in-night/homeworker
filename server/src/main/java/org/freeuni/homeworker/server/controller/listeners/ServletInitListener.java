package org.freeuni.homeworker.server.controller.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.freeuni.homeworker.server.model.source.ConnectionPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletInitListener implements ServletContextListener {

	private static final int NUMBER_OF_CONNECTIONS_IN_USER_MANAGER = 20;

	private static Logger log = LoggerFactory.getLogger(ServletInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext(); //acquire servlet context after initialization
		ObjectMapper objectMapper = new ObjectMapper(); // get object mapper which is thread safe object so can be used
		servletContext.setAttribute(Constants.OBJECT_MAPPER, objectMapper); // put mapper into servlet context
		servletContext.setAttribute(Constants.USER_MANAGER, new UserManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_USER_MANAGER)));
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
