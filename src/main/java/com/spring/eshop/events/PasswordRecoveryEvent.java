package com.spring.eshop.events;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;

public class PasswordRecoveryEvent {

	private final User user;
	private final UserInfo userInfo;

	public PasswordRecoveryEvent(User user, UserInfo userInfo) {
		this.user = user;
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public User getUser() {
		return user;
	}
}
