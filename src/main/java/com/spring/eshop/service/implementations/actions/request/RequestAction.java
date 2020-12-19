package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.service.implementations.AuthGroupService;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class RequestAction {

	private final User user;
	private final UserInfo userInfo;

	protected RequestAction(User user, UserInfo userInfo) {
		this.user = user;
		this.userInfo = userInfo;
	}

	public final ApplicationEvent execute(UserDAO userDAO, UserInfoDAO userInfoDAO, AuthGroupService authGroupService, PasswordEncoder encoder) {
		if (isInvalid(userDAO, user, userInfo)) {
			throw error();
		}
		register(user, userInfo, userDAO, userInfoDAO, authGroupService, encoder);
		return response(user, userInfo.getEmail());
	}

	protected abstract boolean isInvalid(UserDAO userDAO, User user, UserInfo userInfo);

	protected abstract RuntimeException error();

	protected void register(User user, UserInfo userInfo, UserDAO userDAO, UserInfoDAO userInfoDAO, AuthGroupService authGroupService, PasswordEncoder passwordEncoder) {
	}

	protected abstract ApplicationEvent response(User user, String email);
}
