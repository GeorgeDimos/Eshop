package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class ResendActivationEmail extends RequestAction {

	public ResendActivationEmail(User user, UserInfo userInfo) {
		super(user, userInfo);
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO, User user, UserInfo userInfo) {
		return user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("Your account is already confirmed.");
	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new ActivationRequiredEvent(user, email);
	}
}
