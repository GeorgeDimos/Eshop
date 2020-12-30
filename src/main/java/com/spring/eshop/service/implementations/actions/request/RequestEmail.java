package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;

public abstract class RequestEmail {

	private final String username;
	private final String email;

	protected RequestEmail(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public final ApplicationEvent execute(UserDAO userDAO) {
		User user = userDAO.findByUsernameAndUserInfoEmail(username, email).orElseThrow(() -> new InvalidUserInfoException("Invalid user info"));
		if (isInvalid(user)) {
			throw error();
		}
		return response(user);
	}

	protected abstract boolean isInvalid(User user);

	protected abstract RuntimeException error();

	protected abstract ApplicationEvent response(User user);
}
