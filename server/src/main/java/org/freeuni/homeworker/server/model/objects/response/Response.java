package org.freeuni.homeworker.server.model.objects.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the result of the
 * db method.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	@JsonProperty("status")
	private String status;

	@JsonProperty("message")
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
