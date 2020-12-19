package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public class Registration extends ConfirmTemplate {

	public Registration(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(IUserService userService, User user, String password) {
		userService.enableUser(user);
	}
}
