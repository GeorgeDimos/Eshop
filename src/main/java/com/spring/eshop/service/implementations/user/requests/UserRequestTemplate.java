package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public abstract class UserRequestTemplate {

	private final User user;
	private final UserInfo userInfo;

	protected UserRequestTemplate(User user, UserInfo userInfo) {
		this.user = user;
		this.userInfo = userInfo;
	}

	public final void execute(ApplicationEventPublisher publisher, IUserService userService, AuthGroupService authGroupService) {
		if (isInvalid(userService, user, userInfo)) {
			throw error();
		}
		action(userService, authGroupService, user, userInfo);
		publisher.publishEvent(response(user, userInfo.getEmail()));
	}

	protected abstract boolean isInvalid(IUserService userService, User user, UserInfo userInfo);

	protected abstract RuntimeException error();

	protected abstract void action(IUserService userService, AuthGroupService authGroupService, User user, UserInfo userInfo);

	protected abstract ApplicationEvent response(User user, String email);
}
