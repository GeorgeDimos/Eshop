package com.spring.eshop.service.implementations.user.requests;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public abstract class UserRequestTemplate {

	private final ApplicationEventPublisher publisher;

	public UserRequestTemplate(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public final void execute(User user, UserInfo userInfo) {
		isValid(user, userInfo);
		action(user, userInfo);
		publisher.publishEvent(response(user, userInfo.getEmail()));
	}

	protected abstract void isValid(User user, UserInfo userInfo);

	protected abstract void action(User user, UserInfo userInfo);

	protected abstract ApplicationEvent response(User user, String email);
}
