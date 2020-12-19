package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public class ChangePassword extends ConfirmTemplate {

	public ChangePassword(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(IUserService userService, User user, String password) {
		userService.encodePassword(user, password);
	}
}
