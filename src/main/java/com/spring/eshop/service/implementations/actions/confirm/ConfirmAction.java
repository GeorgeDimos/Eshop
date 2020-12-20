package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ConfirmAction {

	protected final String token;

	public ConfirmAction(String token) {
		this.token = token;
	}

	public void execute(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO, PasswordEncoder passwordEncoder) {

		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();

		action(user, passwordEncoder);

		userDAO.save(user);
		confirmationTokenDAO.deleteByToken(token);
	}

	protected abstract void action(User user, PasswordEncoder passwordEncoder);
}
