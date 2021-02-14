package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryConfirmation extends ConfirmAction {

	private final PasswordEncoder passwordEncoder;

	public PasswordRecoveryConfirmation(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO, PasswordEncoder passwordEncoder) {
		super(userDAO, confirmationTokenDAO);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void action(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
	}
}
