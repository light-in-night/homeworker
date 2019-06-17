package org.freeuni.homeworker.server.controller.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.freeuni.homeworker.server.model.managers.postEdit.postEditManager;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;

import org.freeuni.homeworker.server.model.managers.categories.CategoryManagerSQL;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManagerSQL;

import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManagerSQL;
import org.freeuni.homeworker.server.model.managers.posts.PostManagerSQL;
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
	private static final int NUMBER_OF_CONNECTION_IN_POST_MANAGER = 10;
	private static final int NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO = 20;

	private static final int NUMBER_OF_CONNECTIONS_IN_POST_EDIT_MANAGER = 10 ;

	private static final int NUMBER_OF_CONNECTIONS_IN_CATEGORY = 5;
	private static final int NUMBER_OF_CONNECTION_IN_POST_CATEGORY = 10;


	private static Logger log = LoggerFactory.getLogger(ServletInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext(); //acquire servlet context after initialization
		ObjectMapper objectMapper = new ObjectMapper(); // get object mapper which is thread safe object so can be used
		servletContext.setAttribute(ContextKeys.OBJECT_MAPPER, objectMapper); // put mapper into servlet context
		servletContext.setAttribute(ContextKeys.USER_MANAGER, new UserManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_USER_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_MANAGER, new PostManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_LIKE_MANAGER, new PostLikeManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO)));

		servletContext.setAttribute(ContextKeys.POST_EDIT_MANAGER , new postEditManager(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_POST_EDIT_MANAGER)));

		servletContext.setAttribute(ContextKeys.CATEGORY_MANAGER, new CategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_CATEGORY)));
		servletContext.setAttribute(ContextKeys.POST_CATEGORY_MANAGER, new PostCategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_CATEGORY)));


	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
