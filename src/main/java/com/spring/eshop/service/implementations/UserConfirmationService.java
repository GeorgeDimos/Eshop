package com.spring.eshop.service.implementations;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserConfirmationService {

	private final UserService userService;
	private final ApplicationEventPublisher publisher;
	private final ConfirmationTokenService confirmationTokenService;

	@Autowired
	public UserConfirmationService(UserService userService,
								   ApplicationEventPublisher publisher, ConfirmationTokenService confirmationTokenService) {
		this.userService = userService;
		this.publisher = publisher;
		this.confirmationTokenService = confirmationTokenService;
	}

	@Transactional
	public void registerNewUser(User user, UserInfo userInfo) throws UserAlreadyExistsException {
		userService.createUser(user, userInfo);
		publisher.publishEvent(new UserRegistrationEvent(user, userInfo));
	}

	@Transactional
	public User confirmUserRegistration(String token) throws NoSuchElementException {
		User user = confirmationTokenService.getUserFromToken(token);
		userService.enableUser(user);
		confirmationTokenService.deleteByToken(token);
		return user;
	}

	public void sentPasswordRecoveryEmail(String username, String email) {
		User user = userService.getUserByUsernameAndEmail(username, email);
		publisher.publishEvent(new PasswordRecoveryEvent(user, user.getUserInfo()));
	}

	@Transactional
	public void changePassword(String token, String password) throws NoSuchElementException {
		User user = confirmationTokenService.getUserFromToken(token);
		userService.changePassword(user, password);
		confirmationTokenService.deleteByToken(token);
	}
}
