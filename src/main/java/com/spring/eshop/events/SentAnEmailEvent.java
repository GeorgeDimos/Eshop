package com.spring.eshop.events;

import com.spring.eshop.entity.User;

public class SentAnEmailEvent {
	private final User user;
	private final String email;

	protected SentAnEmailEvent(User user, String email) {
		this.user = user;
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public String getEmail() {
		return email;
	}
}
