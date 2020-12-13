package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisterUserService {

	private final UserDAO userDAO;
	private final UserInfoDAO userInfoDAO;
	private final PasswordEncoder passwordEncoder;
	private final AuthGroupDAO authGroupDAO;
	private final ApplicationEventPublisher publisher;
	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public RegisterUserService(UserDAO userDAO, UserInfoDAO userInfoDAO,
							   PasswordEncoder passwordEncoder, AuthGroupDAO authGroupDAO,
							   ApplicationEventPublisher publisher, ConfirmationTokenDAO confirmationTokenDAO) {
		this.userDAO = userDAO;
		this.userInfoDAO = userInfoDAO;
		this.passwordEncoder = passwordEncoder;
		this.authGroupDAO = authGroupDAO;
		this.publisher = publisher;
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	@Transactional
	public void registerNewUser(User user, UserInfo userInfo) {

		if (userDAO.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
		}

		if (userInfoDAO.findByEmail(userInfo.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Email " + userInfo.getEmail() + " already exists");
		}

		user.setEnabled(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.save(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUsername(user.getUsername());
		authGroup.setAuthority("user");
		authGroupDAO.save(authGroup);

		userInfo.setUser(user);
		userInfoDAO.save(userInfo);
		publisher.publishEvent(new UserRegistrationEvent(user, userInfo));
	}

	@Transactional
	public User confirmUserRegistration(String token) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();

		User user = confirmationToken.getUser();
		user.setEnabled(true);
		userDAO.save(user);

		confirmationTokenDAO.delete(confirmationToken);

		return user;
	}

	public void recoverPassword(String username, String email) {
		User user = userDAO.findByUsername(username).orElseThrow(() -> new RuntimeException("Can't find username"));

		if (!user.getEnabled()) {
			throw new RuntimeException("You need to confirm your account first.");
		}

		if (!user.getUserInfo().getEmail().equals(email)) {
			throw new RuntimeException("Wrong email");
		}

		publisher.publishEvent(new PasswordRecoveryEvent(user, user.getUserInfo()));
	}

	public User getUserFromToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		return confirmationToken.getUser();
	}

	@Transactional
	public void changePassword(String token, String password) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();
		user.setPassword(passwordEncoder.encode(password));
		userDAO.save(user);
		confirmationTokenDAO.delete(confirmationToken);
	}
}
