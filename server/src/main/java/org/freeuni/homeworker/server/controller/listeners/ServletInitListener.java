package org.freeuni.homeworker.server.controller.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.freeuni.homeworker.server.model.managers.comments.CommentManagerSQL;
import org.freeuni.homeworker.server.model.managers.session.ConcurrentSessionManager;
import org.freeuni.homeworker.server.model.managers.Login.LoginManagerSQL;
import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.managers.postEdit.PostEditManager;

import org.freeuni.homeworker.server.model.managers.categories.CategoryManagerSQL;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManagerSQL;

import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManagerSQL;
import org.freeuni.homeworker.server.model.managers.posts.PostManagerSQL;
import org.freeuni.homeworker.server.model.managers.session.SessionManager;
import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.freeuni.homeworker.server.model.source.ConnectionPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.Field;

/**
 * Initialises whole application.
 * puts objects in servletContext.
 */
@WebListener
public class ServletInitListener implements ServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(ServletInitListener.class);

	private static final int NUMBER_OF_CONNECTIONS_IN_USER_MANAGER = 20;
	private static final int NUMBER_OF_CONNECTION_IN_POST_MANAGER = 10;
	private static final int NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO = 20;
	private static final int NUMBER_OF_CONNECTIONS_IN_POST_EDIT_MANAGER = 10 ;
	private static final int NUMBER_OF_CONNECTIONS_IN_CATEGORY = 5;
	private static final int NUMBER_OF_CONNECTION_IN_POST_CATEGORY = 10;
	private static final int NUMBER_OF_CONNECTION_IN_LOGIN = 15;
	private static final int NUMBER_OF_CONNECTIONS_IN_COMMENT_MANAGER = 10;

	/**
	 * Initialises all objects needed for application
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext(); //acquire servlet context after initialization
		ObjectMapper objectMapper = new ObjectMapper(); // get object mapper which is thread safe object so can be used
		servletContext.setAttribute(ContextKeys.OBJECT_MAPPER, objectMapper); // put mapper into servlet context
		servletContext.setAttribute(ContextKeys.USER_MANAGER, new UserManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_USER_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_MANAGER, new PostManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_LIKE_MANAGER, new PostLikeManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO)));
		servletContext.setAttribute(ContextKeys.CATEGORY_MANAGER, new CategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_CATEGORY)));
		servletContext.setAttribute(ContextKeys.POST_CATEGORY_MANAGER, new PostCategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_CATEGORY)));
		servletContext.setAttribute(ContextKeys.COMMENT_MANAGER, new CommentManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_COMMENT_MANAGER)));

		SessionManager sessionManager = new ConcurrentSessionManager();
		sessionManager.createCustomSession("test");
		servletContext.setAttribute(ContextKeys.SESSION_MANAGER, sessionManager);
	}

	/**
	 * must destroy all objects after application is finished.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
//		Field[] fields = ManagerNameKeys.class.getFields();
//		try {
//			for (Field field : fields) {
//				String currManagerKey = (String)field.get(ManagerNameKeys.class);
//				log.info("Destroying manager " + currManagerKey + ", as server is stopping.");
//				GeneralManagerSQL currManager = (GeneralManagerSQL) servletContext.getAttribute(currManagerKey);
//				if (currManager != null) {
//					currManager.destroyManager();
//				}
//			}
//		} catch (IllegalAccessException e) {
//			log.error("Error occurred during destroying managers.", e);
//		}
	}
}
