package org.freeuni.homeworker.server.controller.listeners;

/**
 * Saves context keys fro getContext.getAttribute method.
 *
 */
public interface ContextKeys {

	String OBJECT_MAPPER = "objectMapper";

	String USER_MANAGER = "userManager";

	String POST_MANAGER = "postManager";

	String POST_LIKE_MANAGER = "postLikeManager";

	String POST_EDIT_MANAGER = "postEditManager";

	String CATEGORY_MANAGER = "categoryManager";

	String POST_CATEGORY_MANAGER = "postCategoryManager";
}
