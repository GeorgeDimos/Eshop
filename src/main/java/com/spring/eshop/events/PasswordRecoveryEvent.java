package com.spring.eshop.events;

import com.spring.eshop.entity.User;

public class PasswordRecoveryEvent extends SendEmailEvent {

	public PasswordRecoveryEvent(User user, String email) {
		super(user, email);
	}
}
