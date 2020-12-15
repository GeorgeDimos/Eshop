package com.spring.eshop.service.implementations;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
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
		publisher.publishEvent(new UserRegistrationEvent(user, userInfo.getEmail()));
	}

	@Override
	@Transactional
	public User confirmUserRegistration(String token) throws NoSuchElementException {
		User user = confirmationTokenService.getUserByToken(token);
		userService.enableUser(user);
		confirmationTokenService.deleteToken(token);
		return user;
	}

	@Override
	public void resendActivationEmail(String username, String email) {
		User user = userService.getUserByUsernameAndEmail(username, email);
		if (user.getEnabled()) {
			throw new InvalidUserInfoException("Your account is already confirmed.");
		}
		publisher.publishEvent(new UserRegistrationEvent(user, email));
	}

	@Override
	public void sendPasswordRecoveryEmail(String username, String email) {
		User user = userService.getUserByUsernameAndEmail(username, email);
		if (!user.getEnabled()) {
			throw new InvalidUserInfoException("You need to confirm your account first.");
		}
		publisher.publishEvent(new PasswordRecoveryEvent(user, email));
	}

	@Override
	@Transactional
	public void confirmPasswordChange(String token, String password) throws NoSuchElementException {
		User user = confirmationTokenService.getUserByToken(token);
		userService.changePassword(user, password);
		confirmationTokenService.deleteToken(token);
	}
}