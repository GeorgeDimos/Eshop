package com.spring.eshop.events;

import com.spring.eshop.entity.User;

public class UserRegistrationEvent extends SendEmailEvent {

	public UserRegistrationEvent(User user, String email) {
		super(user, email);
	}
}
