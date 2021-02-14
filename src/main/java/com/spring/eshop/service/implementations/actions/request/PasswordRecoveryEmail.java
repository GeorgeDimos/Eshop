package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryEmail extends RequestEmail {

	public PasswordRecoveryEmail(UserDAO userDAO, ApplicationEventPublisher publisher) {
		super(userDAO, publisher);
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
