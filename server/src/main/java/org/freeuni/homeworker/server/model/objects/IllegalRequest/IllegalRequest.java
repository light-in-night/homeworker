package org.freeuni.homeworker.server.model.objects.IllegalRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IllegalRequest {

	@JsonProperty("code")
	private IllegalRequestTypes type;

	public IllegalRequestTypes getType() {
		return type;
	}

	public void setType(IllegalRequestTypes type) {
		this.type = type;
	}
}
