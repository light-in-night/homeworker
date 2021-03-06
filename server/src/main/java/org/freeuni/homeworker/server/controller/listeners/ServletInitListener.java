package org.freeuni.homeworker.server.controller.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.managers.categories.CategoryManagerSQL;
import org.freeuni.homeworker.server.model.managers.comments.CommentManagerSQL;
import org.freeuni.homeworker.server.model.managers.postCategory.PostCategoryManagerSQL;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManagerSQL;
import org.freeuni.homeworker.server.model.managers.posts.PostManagerSQL;
import org.freeuni.homeworker.server.model.managers.session.SessionManagerImpl;
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
 * @Author Guram Tkesheladze
 */
@WebListener
public class ServletInitListener implements ServletContextListener {

	private static final int NUMBER_OF_CONNECTIONS_IN_USER_MANAGER = 20;
	private static final int NUMBER_OF_CONNECTION_IN_POST_MANAGER = 10;
	private static final int NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO = 20;
	private static final int NUMBER_OF_CONNECTIONS_IN_CATEGORY = 5;
	private static final int NUMBER_OF_CONNECTION_IN_POST_CATEGORY = 10;
	private static final int NUMBER_OF_CONNECTION_IN_COMMENT_MANAGER = 10;

	private static final Logger log = LoggerFactory.getLogger(ServletInitListener.class);

	/**
	 * Initialises all objects needed for application
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext(); //acquire servlet context after initialization
		servletContext.setAttribute(ContextKeys.OBJECT_MAPPER, new ObjectMapper()); // put mapper into servlet context
		servletContext.setAttribute(ContextKeys.USER_MANAGER, new UserManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_USER_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_MANAGER, new PostManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_MANAGER)));
		servletContext.setAttribute(ContextKeys.POST_LIKE_MANAGER, new PostLikeManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_POST_LIKE_DAO)));
		servletContext.setAttribute(ContextKeys.CATEGORY_MANAGER, new CategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTIONS_IN_CATEGORY)));
		servletContext.setAttribute(ContextKeys.POST_CATEGORY_MANAGER, new PostCategoryManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_POST_CATEGORY)));
		servletContext.setAttribute(ContextKeys.COMMENT_MANAGER, new CommentManagerSQL(ConnectionPoolFactory.buildConnectionPool(NUMBER_OF_CONNECTION_IN_COMMENT_MANAGER)));
		servletContext.setAttribute(ContextKeys.SESSION_MANAGER, new SessionManagerImpl());
	}

	/*
	 * This method destroy all the managers using reflection too automate this task.
	 * @ Author Guga tkesheladze
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		Field[] managerFields = ManagerNameKeys.class.getFields();
		try { //using reflection to iterate throug all constants in manager keys inteface
			for (Field field : managerFields) { // illegal state will occur if there is a field in the interface
				String currManagerKey = (String)field.get(ManagerNameKeys.class); // which is not associated
				log.info("Destroying manager " + currManagerKey + ", as server is stopping.");
				GeneralManagerSQL currentManager = (GeneralManagerSQL) context.getAttribute(currManagerKey);
				if (currentManager != null) {
					currentManager.destroyManager();
				}
			}
		} catch (IllegalAccessException e) { //UNTESTABLE
			log.error("Error occurred during destroying managers.", e);
		}
	}
}
