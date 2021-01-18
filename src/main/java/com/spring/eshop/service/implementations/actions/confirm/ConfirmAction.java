package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;

public abstract class ConfirmAction {

	private final String token;

	public ConfirmAction(String token) {
		this.token = token;
	}

	public void execute(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO) {

		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();

		action(user);

		userDAO.save(user);
		confirmationTokenDAO.deleteByToken(token);
	}

	protected abstract void action(User user);
}
