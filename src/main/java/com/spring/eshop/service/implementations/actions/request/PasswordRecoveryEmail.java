package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.entity.User;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class PasswordRecoveryEmail extends RequestEmail {

	public PasswordRecoveryEmail(String username, String email) {
		super(username, email);
	}

	@Override
	protected boolean isInvalid(User user) {
		return !user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("You need to confirm your account first.");
	}

	@Override
	protected ApplicationEvent response(User user) {
		return new PasswordRecoveryEvent(user, user.getUserInfo().getEmail());
	}
}
