package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordRecoveryConfirmation extends ConfirmAction {

	public PasswordRecoveryConfirmation(String token, String password) {
		super(token, password);
	}

	@Override
	protected void action(PasswordEncoder passwordEncoder, User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
	}
}
