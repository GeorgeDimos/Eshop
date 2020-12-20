package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class ResendActivationEmail extends RequestAction {

	public ResendActivationEmail(User user) {
		super(user);
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO) {
		return user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("Your account is already confirmed.");
	}

	@Override
	protected ApplicationEvent response() {
		return new ActivationRequiredEvent(user, user.getUserInfo().getEmail());
	}
}
