package org.freeuni.homeworker.server.model.objects.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL) // annotation to include null values when converted to json
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
	@JsonProperty("userId")
	private Long userId;

	@JsonProperty("loggedIn")
	private boolean loggedIn;

	@JsonProperty("numberOfUnsuccessfulAttempts")
	private int numberOfUnsuccessfulAttempts;

	@JsonProperty("lastActivityTime")
	private Date lastActivityTime;

	public Session() {
		userId = null;
		loggedIn = false;
		numberOfUnsuccessfulAttempts = 0;
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

	public synchronized int getNumberOfUnsuccessfulAttempts() {
		return numberOfUnsuccessfulAttempts;
	}

	public synchronized void setNumberOfUnsuccessfulAttempts(int numberOfUnsuccessfulAttempts) {
		this.numberOfUnsuccessfulAttempts = numberOfUnsuccessfulAttempts;
	}

	public synchronized Date getLastActivityTime() {
		return new Date(lastActivityTime.getTime());
	}

	public synchronized void setLastActivityTime(Date lastActivityTime) {
		this.lastActivityTime = new Date(lastActivityTime.getTime());
	}

	@Override
	public String toString() {
		return "Session{" +
				"userId=" + userId +
				", loggedIn=" + loggedIn +
				", numberOfUnsuccessfulAttempts=" + numberOfUnsuccessfulAttempts +
				", lastActivityTime=" + lastActivityTime +
				'}';
	}


}
