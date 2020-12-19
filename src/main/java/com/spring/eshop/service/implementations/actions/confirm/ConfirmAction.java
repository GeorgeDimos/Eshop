package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ConfirmAction {

	private final String token;
	protected final String password;

	public ConfirmAction(String token, String password) {
		this.token = token;
		this.password = password;
	}

	public void execute(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO, PasswordEncoder passwordEncoder) {

		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();

		action(passwordEncoder, user, password);

		userDAO.save(user);
		confirmationTokenDAO.deleteByToken(token);
	}

	protected abstract void action(PasswordEncoder passwordEncoder, User user, String password);
}
