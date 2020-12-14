package com.spring.eshop.service.implementations;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import com.spring.eshop.service.interfaces.IUserConfirmationService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserConfirmationService implements IUserConfirmationService {

	private final IUserService userService;
	private final ApplicationEventPublisher publisher;
	private final IConfirmationTokenService confirmationTokenService;

	@Autowired
	public UserConfirmationService(IUserService userService,
								   ApplicationEventPublisher publisher, IConfirmationTokenService confirmationTokenService) {
		this.userService = userService;
		this.publisher = publisher;
		this.confirmationTokenService = confirmationTokenService;
	}

	@Override
	@Transactional
	public void registerUser(User user, UserInfo userInfo) throws UserAlreadyExistsException {
		userService.createUser(user, userInfo);
		publisher.publishEvent(new UserRegistrationEvent(user, userInfo));
	}

	@Override
	@Transactional
	public User confirmUserRegistration(String token) throws NoSuchElementException {
		User user = confirmationTokenService.getUserByToken(token);
		userService.enableUser(user);
		confirmationTokenService.deleteByToken(token);
		return user;
	}

	@Override
	public void sendPasswordRecoveryEmail(String username, String email) {
		User user = userService.getUserByUsernameAndEmail(username, email);
		publisher.publishEvent(new PasswordRecoveryEvent(user, user.getUserInfo()));
	}

	@Override
	@Transactional
	public void changePassword(String token, String password) throws NoSuchElementException {
		User user = confirmationTokenService.getUserByToken(token);
		userService.changePassword(user, password);
		confirmationTokenService.deleteByToken(token);
	}
}
