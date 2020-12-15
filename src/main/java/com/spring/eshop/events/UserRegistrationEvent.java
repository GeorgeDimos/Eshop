package com.spring.eshop.events;

import com.spring.eshop.entity.User;

public class UserRegistrationEvent extends SentAnEmailEvent {

	public UserRegistrationEvent(User user, String email) {
		super(user, email);
	}
}
