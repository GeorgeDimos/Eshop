package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.actions.confirm.RegistrationConfirmation;
import com.spring.eshop.service.interfaces.IUserRegistration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserRegistration implements IUserRegistration {
	private final PasswordEncoder passwordEncoder;
	private final UserDAO userDAO;
	private final ApplicationEventPublisher publisher;
	private final RegistrationConfirmation registrationConfirmation;

	public UserRegistration(PasswordEncoder passwordEncoder, UserDAO userDAO, ApplicationEventPublisher publisher, RegistrationConfirmation registrationConfirmation) {
		this.passwordEncoder = passwordEncoder;
		this.userDAO = userDAO;
		this.publisher = publisher;
		this.registrationConfirmation = registrationConfirmation;
	}

	@Transactional
	public void register(User user, UserInfo userInfo) {
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

	public void confirm(String token) {
		registrationConfirmation.execute(token, null);
	}
}
