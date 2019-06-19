package org.freeuni.homeworker.server.model.objects.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.freeuni.homeworker.server.utils.StringUtils;

import java.util.Objects;

/**
 * Simple user class that can save all the information about user
 * can be converted to and from JSON very easily using JACKSON
 */
@SuppressWarnings("WeakerAccess")
@JsonInclude(JsonInclude.Include.NON_NULL) // annotation to include null values when converted to json
public class User {

	@JsonProperty("id")
	private long id; // id of the user will be primary key in the database . this is automatically assigned to user by database after persist.

	@JsonProperty("firstName")
	private String firstName; // first name of the user

	@JsonProperty("lastName")
	private String lastName; // last name of the user

	@JsonProperty("gender")
	private String gender; // gender of the user user may be unwilling to share this info so this field can be null

	@JsonProperty("email")
	private String email; // email of the user

	@JsonProperty("password")
	private String password; // password of the user

	public User() {
	}

	public User(long id, String firstName, String lastName, String gender, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	/**
	 * Returns if the given user entry is valid.
	 * @return true if valid, false otherwise.
	 */
	public boolean isValid() {
		boolean valid = StringUtils.isNotBlank(firstName);
		valid = valid && StringUtils.isNotBlank(lastName);
		valid = valid && StringUtils.isNotBlank(email);
		valid = valid && StringUtils.isNotBlank(password);
		return valid;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	/**
	 * The two User objects are equal if all their
	 * fields are equal.
	 *
	 * @param o other object
	 * @return true if fields are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getId() == user.getId() &&
				getFirstName().equals(user.getFirstName()) &&
				getLastName().equals(user.getLastName()) &&
				Objects.equals(getGender(), user.getGender()) &&
				getEmail().equals(user.getEmail()) &&
				getPassword().equals(user.getPassword());
	}

	/**
	 * returns a combination of hashcodes of all
	 * fields.
	 *
	 * @return combination of hashcodes of all fields.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getGender(), getEmail(), getPassword());
	}
}
