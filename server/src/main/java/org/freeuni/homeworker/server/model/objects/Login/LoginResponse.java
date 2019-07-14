package org.freeuni.homeworker.server.model.objects.Login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {


	@JsonProperty("sessionId")
	private String sessionId;

	@JsonProperty("userId")
	private Long userId;

	@JsonProperty("loggedIn")
	private Boolean loggedIn;

	public LoginResponse(String sessionId, Long userId, Boolean loggedIn) {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"sessionId='" + sessionId + '\'' +
				", userId=" + userId +
				", loggedIn=" + loggedIn +
				'}';
	}
}
