package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;

import javax.transaction.Transactional;

public abstract class ConfirmAction {

	private final UserDAO userDAO;
	private final ConfirmationTokenDAO confirmationTokenDAO;

	public ConfirmAction(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO) {
		this.userDAO = userDAO;
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	@Transactional
	public void execute(String token, String password) {

		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();

		action(user, password);

		userDAO.save(user);
		confirmationTokenDAO.deleteByToken(token);
	}

	protected abstract void action(User user, String password);
}
