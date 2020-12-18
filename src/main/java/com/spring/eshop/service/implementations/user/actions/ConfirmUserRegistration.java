package com.spring.eshop.service.implementations.user.actions;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public class ConfirmUserRegistration extends UserConfirmTemplate {

	public ConfirmUserRegistration(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(IUserService userService, User user, String password) {
		userService.enableUser(user);
	}
}
