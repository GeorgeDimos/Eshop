package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistration extends RequestRegistration {

	private final UserInfo userInfo;

	public UserRegistration(User user, UserInfo userInfo) {

		super(user);
		this.userInfo = userInfo;
	}

	@Override
	protected void checkAndCreate(UserDAO userDAO, PasswordEncoder encoder, ProductDAO productDAO) {
		if (userDAO.existsByUsernameOrUserInfoEmail(user.getUsername(), userInfo.getEmail())) {
			throw new UserAlreadyExistsException("User already exists");
		}
		user.setPassword(encoder.encode(user.getPassword()));

		user.setUserInfo(userInfo);
		userInfo.setUser(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUser(user);
		user.setAuthGroup(authGroup);
	}

	@Override
	protected ApplicationEvent response() {

		return new ActivationRequiredEvent(user, userInfo.getEmail());
	}
}
