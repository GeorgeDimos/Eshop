package com.spring.eshop.service.implementations.user.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public class ConfirmChangePassword extends UserConfirmTemplate {

	public ConfirmChangePassword(ConfirmationTokenDAO confirmationTokenDAO,
								 IUserService userService) {
		super(confirmationTokenDAO, userService);
	}

	@Override
	protected void action(User user, String password) {
		userService.encodePassword(user, password);
	}
}
