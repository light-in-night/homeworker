package org.freeuni.demo.server.model;


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/*
 * dumb object only exists only because i wanted to show you json request response
 */
public class SimpleObject implements Serializable {

	@JsonProperty("saxeli") // annotation that defines the name of the variable property in json
	private String firstName;

	@JsonProperty("gvari")
	private String lastName;

	public SimpleObject(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
