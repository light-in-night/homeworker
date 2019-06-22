package org.freeuni.homeworker.server.model.objects.Login;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;



	public LoginRequest(){}

	public LoginRequest(String email, String password){
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		return Objects.hash(getEmail(), getPassword());
	}

	@Override
	public String toString() {
		String result = "Email : " + getEmail() +"\n";
		result += "Password : " + getPassword() + "\n";
		return result;
	}

	public boolean containsNull(){
		return getEmail() == null || getPassword() == null;
	}

}
