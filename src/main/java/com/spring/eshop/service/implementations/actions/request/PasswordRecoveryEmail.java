package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public class PasswordRecoveryEmail extends RequestAction {

	public PasswordRecoveryEmail(User user, UserInfo userInfo) {
		super(user, userInfo);
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO, User user, UserInfo userInfo) {
		return !user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("You need to confirm your account first.");
	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new PasswordRecoveryEvent(user, email);
	}
}
