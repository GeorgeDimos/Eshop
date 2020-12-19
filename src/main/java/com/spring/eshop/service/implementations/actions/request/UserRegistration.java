package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.AuthGroupService;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

public class UserRegistration extends RequestAction {

	public UserRegistration(User user, UserInfo userInfo) {
		super(user, userInfo);
	}

	@Override
	protected boolean isInvalid(UserDAO userDAO, User user, UserInfo userInfo) {
		return userDAO.existsByUsernameOrUserInfoEmail(user.getUsername(), userInfo.getEmail());
	}

	@Override
	protected RuntimeException error() {
		return new UserAlreadyExistsException("User already exists");
	}

	@Override
	@Transactional
	protected void register(User user, UserInfo userInfo, UserDAO userDAO, UserInfoDAO userInfoDAO, AuthGroupService authGroupService, PasswordEncoder passwordEncoder) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.save(user);

		userInfo.setUser(user);
		userInfoDAO.save(userInfo);

		authGroupService.createAuthGroupForUser(user.getUsername());
	}

	@Override
	protected ApplicationEvent response(User user, String email) {
		return new ActivationRequiredEvent(user, email);
	}
}
