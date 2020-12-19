package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationConfirmation extends ConfirmAction {

	public RegistrationConfirmation(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(PasswordEncoder passwordEncoder, User user, String password) {
		user.setEnabled(true);
	}
}
