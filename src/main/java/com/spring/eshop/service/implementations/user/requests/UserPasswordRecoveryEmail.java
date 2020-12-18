package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.context.ApplicationEvent;

public class UserPasswordRecoveryEmail extends UserRequestTemplate {

	public UserPasswordRecoveryEmail(User user, UserInfo userInfo) {
		super(user, userInfo);
	}

	@Override
	protected boolean isInvalid(IUserService userService, User user, UserInfo userInfo) {
		return !user.getEnabled();
	}

	@Override
	protected RuntimeException error() {
		return new InvalidUserInfoException("You need to confirm your account first.");
	}

	@Override
	protected void action(IUserService userService, AuthGroupService authGroupService, User user, UserInfo userInfo) {

	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new PasswordRecoveryEvent(user, email);
	}
}
