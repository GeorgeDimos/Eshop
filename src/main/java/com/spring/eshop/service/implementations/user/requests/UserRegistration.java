package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.context.ApplicationEvent;

public class UserRegistration extends UserRequestTemplate {

	public UserRegistration(User user, UserInfo userInfo) {
		super(user, userInfo);
	}

	@Override
	protected boolean isInvalid(IUserService userService, User user, UserInfo userInfo) {
		return (userService.usernameInUse(user.getUsername()) || userService.emailInUse(userInfo.getEmail()));
	}

	@Override
	protected RuntimeException error() {
		return new UserAlreadyExistsException("User already exists");
	}

	@Override
	protected void action(IUserService userService, AuthGroupService authGroupService, User user, UserInfo userInfo) {
		userService.createUser(user, userInfo);

		authGroupService.createAuthGroupForUser(user.getUsername());
	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new UserRegistrationEvent(user, email);
	}
}
