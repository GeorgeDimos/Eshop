package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class RequestAction {

	protected final User user;

	protected RequestAction(User user) {
		this.user = user;
	}

	public final ApplicationEvent execute(UserDAO userDAO, PasswordEncoder encoder) {
		if (isInvalid(userDAO)) {
			throw error();
		}
		register(userDAO, encoder);
		return response();
	}

	protected abstract boolean isInvalid(UserDAO userDAO);

	protected abstract RuntimeException error();

	protected void register(UserDAO userDAO, PasswordEncoder passwordEncoder) {
	}

	protected abstract ApplicationEvent response();
}
