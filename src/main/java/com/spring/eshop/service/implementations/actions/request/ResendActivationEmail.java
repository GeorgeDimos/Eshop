package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.entity.User;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class ResendActivationEmail extends RequestEmail {

	public ResendActivationEmail(String username, String email) {
		super(username, email);
	}

	@Override
	protected boolean isInvalid(User user) {
		return user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("Your account is already confirmed.");
	}

	@Override
	protected ApplicationEvent response(User user) {
		return new ActivationRequiredEvent(user, user.getUserInfo().getEmail());
	}
}
