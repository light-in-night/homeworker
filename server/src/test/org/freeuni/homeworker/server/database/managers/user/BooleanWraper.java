package org.freeuni.homeworker.server.database.managers.user;

public class BooleanWraper {

	private boolean value;

	public BooleanWraper(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
