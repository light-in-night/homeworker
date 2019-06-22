package org.freeuni.homeworker.server.model.objects.Login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {


	@JsonProperty("sessionId")
	private String sessionId;

	@JsonProperty("userId")
	private long userId;

	@JsonProperty("loggedIn")
	private boolean loggedIn;


	public LoginResponse(String sessionId, long userId, boolean loggedIn) {
		this.sessionId = sessionId;
		this.userId = userId;
		this.loggedIn = loggedIn;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionID) {
		this.sessionId = sessionID;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
