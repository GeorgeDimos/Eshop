package com.spring.eshop.service.implementations.user.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;

public abstract class UserConfirmTemplate {

	private final String token;
	protected final String password;

	public UserConfirmTemplate(String token, String password) {
		this.token = token;
		this.password = password;
	}

	public void execute(ConfirmationTokenDAO confirmationTokenDAO, IUserService userService) {
		User user = getUser(confirmationTokenDAO, token);
		action(userService, user, password);
		deleteToken(confirmationTokenDAO, token);
	}

	protected User getUser(ConfirmationTokenDAO confirmationTokenDAO, String token) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		return confirmationToken.getUser();
	}

	protected abstract void action(IUserService userService, User user, String password);

	protected void deleteToken(ConfirmationTokenDAO confirmationTokenDAO, String token) {
		confirmationTokenDAO.deleteByToken(token);
	}
}
