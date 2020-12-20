package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationConfirmation extends ConfirmAction {

	public RegistrationConfirmation(String token) {
		super(token);
	}

	@Override
	protected void action(User user, PasswordEncoder passwordEncoder) {
		user.setEnabled(true);
	}
}
