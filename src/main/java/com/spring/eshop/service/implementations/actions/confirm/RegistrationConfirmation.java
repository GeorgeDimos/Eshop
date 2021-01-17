package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;

public class RegistrationConfirmation extends ConfirmAction {

	public RegistrationConfirmation(String token) {
		super(token);
	}

	@Override
	protected void action(User user) {
		user.setEnabled(true);
	}
}
