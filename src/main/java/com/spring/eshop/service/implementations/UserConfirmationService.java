package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.PasswordRecoveryEvent;
import com.spring.eshop.events.UserRegistrationEvent;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.interfaces.IUserConfirmationService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserConfirmationService implements IUserConfirmationService {

	private final IUserService userService;
	private final AuthGroupService authGroupService;
	private final ApplicationEventPublisher publisher;
	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public UserConfirmationService(IUserService userService,
								   AuthGroupService authGroupService, ApplicationEventPublisher publisher, ConfirmationTokenDAO confirmationTokenDAO) {
		this.userService = userService;
		this.authGroupService = authGroupService;
		this.publisher = publisher;
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	@Override
	@Transactional
	public void registerUser(User user, UserInfo userInfo) throws UserAlreadyExistsException {
		if (userService.usernameInUse(user.getUsername())) {
			throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
		}

		if (userService.emailInUse(userInfo.getEmail())) {
			throw new UserAlreadyExistsException("Email " + userInfo.getEmail() + " already exists");
		}

		userService.createUser(user, userInfo);
		authGroupService.createAuthGroupForUser(user.getUsername());
		publisher.publishEvent(new UserRegistrationEvent(user, userInfo.getEmail()));
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
	public void confirmPasswordChange(String token, String password) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();
		userService.encodePassword(user, password);
		confirmationTokenDAO.deleteByToken(token);
	}

	@Override
	@Transactional
	public void confirmUserRegistration(String token) {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow();
		User user = confirmationToken.getUser();
		userService.enableUser(user);
		confirmationTokenDAO.deleteByToken(token);
	}
}