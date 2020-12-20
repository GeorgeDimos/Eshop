package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

public class UserRegistration extends RequestAction {

	private final UserInfo userInfo;

	public UserRegistration(User user, UserInfo userInfo) {

		super(user);
		this.userInfo = userInfo;
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO) {
		return userDAO.existsByUsernameOrUserInfoEmail(user.getUsername(), userInfo.getEmail());
	}

	@Override
	protected RuntimeException error() {
		return new UserAlreadyExistsException("User already exists");
	}

	@Override
	@Transactional
	protected void register(UserDAO userDAO, PasswordEncoder passwordEncoder) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setUserInfo(userInfo);
		userInfo.setUser(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUser(user);
		user.setAuthGroup(authGroup);

		userDAO.save(user);
	}

	@Override
	protected ApplicationEvent response() {
		return new ActivationRequiredEvent(user, userInfo.getEmail());
	}
}
