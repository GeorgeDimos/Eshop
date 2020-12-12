package com.spring.eshop.events;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;

public class UserRegistrationEvent {

	private final User user;
	private final UserInfo userInfo;

	public UserRegistrationEvent(User user, UserInfo userInfo) {
		this.user = user;
		this.userInfo = userInfo;
	}

	public User getUser() {
		return user;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
}
