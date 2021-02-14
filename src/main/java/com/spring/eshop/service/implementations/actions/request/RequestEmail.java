package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public abstract class RequestEmail {

	private final UserDAO userDAO;
	private final ApplicationEventPublisher publisher;

	public RequestEmail(UserDAO userDAO, ApplicationEventPublisher publisher) {
		this.userDAO = userDAO;
		this.publisher = publisher;
	}

	public final void execute(String username, String email) {
		User user = userDAO.findByUsernameAndUserInfoEmail(username, email).orElseThrow(() -> new InvalidUserInfoException("Invalid user info"));
		if (isInvalid(user)) {
			throw error();
		}
		publisher.publishEvent(response(user));
	}

	protected abstract boolean isInvalid(User user);

	protected abstract RuntimeException error();

	protected abstract ApplicationEvent response(User user);
}
