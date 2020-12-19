package com.spring.eshop.events;

import com.spring.eshop.entity.User;

public class ActivationRequiredEvent extends SendEmailEvent {

	public ActivationRequiredEvent(User user, String email) {
		super(user, email);
	}
}
