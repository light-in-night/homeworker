package org.freeuni.demo.server;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletInizializator implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext(); //acquire servlet context after initialization
		ObjectMapper objectMapper = new ObjectMapper(); // get object mapper which is thread safe object so can be used
		servletContext.setAttribute("mapper", objectMapper); // put mapper intop servlet context
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
