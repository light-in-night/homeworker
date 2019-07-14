package org.freeuni.homeworker.server.controller.listeners;

/**
 * Saves context keys fro getContext.getAttribute method.
 *
 */
public interface ContextKeys extends ManagerNameKeys {

	String OBJECT_MAPPER = "objectMapper";

	String SESSION_MANAGER = "sessionManager";

	String SESSION_ID = "sessionId";

	String ILLEGAL_REQUEST =  "/illegalRequest";
}
