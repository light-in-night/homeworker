package org.freeuni.homeworker.server.controller.session;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
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

	public synchronized Long getUserId() {
		return userId;
	}

	public synchronized void setUserId(Long userId) {
		this.userId = userId;
	}

	public synchronized boolean isLoggedIn() {
		return loggedIn;
	}

	public synchronized void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public synchronized int getNumberOfUnSuccessfulAttempts() {
		return numberOfUnSuccessfulAttempts;
	}

	public synchronized void setNumberOfUnSuccessfulAttempts(int numberOfUnSuccessfulAttempts) {
		this.numberOfUnSuccessfulAttempts = numberOfUnSuccessfulAttempts;
	}

	public synchronized Date getLastActivityTime() {
		return lastActivityTime;
	}

	public synchronized void setLastActivityTime(Date lastActivityTime) {
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
