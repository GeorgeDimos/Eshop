package com.spring.eshop.service.implementations.user.actions;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public class ConfirmChangePassword extends UserConfirmTemplate {

	public ConfirmChangePassword(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(IUserService userService, User user, String password) {
		userService.encodePassword(user, password);
	}
}
