package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class PasswordRecoveryEmail extends RequestAction {

	public PasswordRecoveryEmail(User user) {
		super(user);
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO) {
		return !user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("You need to confirm your account first.");
	}

	@Override
	protected ApplicationEvent response() {
		return new PasswordRecoveryEvent(user, user.getUserInfo().getEmail());
	}
}
