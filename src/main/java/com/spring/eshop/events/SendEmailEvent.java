package com.spring.eshop.events;

import com.spring.eshop.entity.User;
import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {
	private final User user;
	private final String email;

	protected SendEmailEvent(User user, String email) {
		super(user);
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
