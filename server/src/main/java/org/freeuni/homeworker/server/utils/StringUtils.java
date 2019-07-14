package org.freeuni.homeworker.server.utils;

/**
 * Some random utility function for our project
 */
public class StringUtils {

	public static boolean isNotBlank(String s) {
		return s != null && !s.trim().equals("");
	}

	public static boolean isBlank(String s) {
		return !isNotBlank(s);
	}
}
