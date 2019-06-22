package org.freeuni.homeworker.server.controller.session;

import java.util.Date;

public class Session {

	private Long userId;

	private boolean loggedIn;

	private int numberOfUnSuccessfulAttempts;

	private Date lastActivityTime;

	public Session() {
		userId = null;
		loggedIn = false;
		numberOfUnSuccessfulAttempts = 0;
		lastActivityTime = new Date();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int getNumberOfUnSuccessfulAttempts() {
		return numberOfUnSuccessfulAttempts;
	}

	public void setNumberOfUnSuccessfulAttempts(int numberOfUnSuccessfulAttempts) {
		this.numberOfUnSuccessfulAttempts = numberOfUnSuccessfulAttempts;
	}

	public Date getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(Date lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	@Override
	public String toString() {
		return "Session{" +
				"userId=" + userId +
				", loggedIn=" + loggedIn +
				", numberOfUnSuccessfulAttempts=" + numberOfUnSuccessfulAttempts +
				", lastActivityTime=" + lastActivityTime +
				'}';
	}
}
