package com.spring.eshop.service.implementations.user.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public abstract class UserConfirmTemplate {

	private final ConfirmationTokenDAO confirmationTokenDAO;
	protected final IUserService userService;

	public UserConfirmTemplate(ConfirmationTokenDAO confirmationTokenDAO, IUserService userService) {
		this.confirmationTokenDAO = confirmationTokenDAO;
		this.userService = userService;
	}

	public void execute(String token, String password) {
		User user = getUser(token);
		action(user, password);
		deleteToken(token);
	}

	protected User getUser(String token) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		return confirmationToken.getUser();
	}

	protected abstract void action(User user, String password);

	protected void deleteToken(String token) {
		confirmationTokenDAO.deleteByToken(token);
	}
}
