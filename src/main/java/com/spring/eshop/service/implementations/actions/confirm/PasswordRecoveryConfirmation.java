package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordRecoveryConfirmation extends ConfirmAction {

	private final String password;
	private final PasswordEncoder passwordEncoder;

	public PasswordRecoveryConfirmation(String token, String password, PasswordEncoder passwordEncoder) {
		super(token);
		this.password = password;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void action(User user) {
		user.setPassword(passwordEncoder.encode(password));
	}
}
