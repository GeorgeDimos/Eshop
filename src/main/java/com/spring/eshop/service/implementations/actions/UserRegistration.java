package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserRegistration {
	private final PasswordEncoder passwordEncoder;
	private final UserDAO userDAO;
	private final ApplicationEventPublisher publisher;

	public UserRegistration(PasswordEncoder passwordEncoder, UserDAO userDAO, ApplicationEventPublisher publisher) {
		this.passwordEncoder = passwordEncoder;
		this.userDAO = userDAO;
		this.publisher = publisher;
	}

	@Transactional
	public void execute(User user, UserInfo userInfo) {
		if (userDAO.existsByUsernameOrUserInfoEmail(user.getUsername(), userInfo.getEmail())) {
			throw new UserAlreadyExistsException("User already exists");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setUserInfo(userInfo);
		userInfo.setUser(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUser(user);
		user.setAuthGroup(authGroup);
		userDAO.save(user);

		publisher.publishEvent(new ActivationRequiredEvent(user, userInfo.getEmail()));
	}
}
