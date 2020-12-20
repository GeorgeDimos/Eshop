package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordRecoveryConfirmation extends ConfirmAction {

	private final String password;

	public PasswordRecoveryConfirmation(String token, String password) {
		super(token);
		this.password = password;
	}

	@Override
	protected void action(User user, PasswordEncoder passwordEncoder) {
		user.setPassword(passwordEncoder.encode(password));
	}
}
