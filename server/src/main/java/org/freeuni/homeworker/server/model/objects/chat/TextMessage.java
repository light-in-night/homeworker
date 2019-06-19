package org.freeuni.homeworker.server.model.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class TextMessage implements Serializable {

	@JsonProperty("id")
	private long id;

	@JsonProperty("senderId")
	private long senderId;

	@JsonProperty("receiverId")
	private long receiverId;

	@JsonProperty("message")
	private String message;

	@JsonProperty("sendTime")
	private Date sendTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
