package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class UserPasswordRecoveryEmail extends UserRequestTemplate {

	public UserPasswordRecoveryEmail(ApplicationEventPublisher publisher) {
		super(publisher);
	}

	@Override
	protected void isValid(User user, UserInfo userInfo) {
		if (!user.getEnabled()) {
			throw new InvalidUserInfoException("You need to confirm your account first.");
		}
	}

	@Override
	protected void action(User user, UserInfo userInfo) {

	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new PasswordRecoveryEvent(user, email);
	}
}
