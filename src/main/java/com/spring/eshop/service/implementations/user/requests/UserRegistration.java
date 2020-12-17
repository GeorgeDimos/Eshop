package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class UserRegistration extends UserRequestTemplate {

	private final IUserService userService;
	private final AuthGroupService authGroupService;

	public UserRegistration(ApplicationEventPublisher publisher, IUserService userService, AuthGroupService authGroupService) {
		super(publisher);
		this.userService = userService;
		this.authGroupService = authGroupService;
	}

	@Override
	protected void isValid(User user, UserInfo userInfo) {
		if (userService.usernameInUse(user.getUsername())) {
			throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
		}

		if (userService.emailInUse(userInfo.getEmail())) {
			throw new UserAlreadyExistsException("Email " + userInfo.getEmail() + " already exists");
		}
	}

	@Override
	protected void action(User user, UserInfo userInfo) {
		userService.createUser(user, userInfo);

		authGroupService.createAuthGroupForUser(user.getUsername());
	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new UserRegistrationEvent(user, email);
	}
}
