package com.spring.eshop.service.implementations.user;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.implementations.user.actions.ConfirmChangePassword;
import com.spring.eshop.service.implementations.user.actions.ConfirmUserRegistration;
import com.spring.eshop.service.implementations.user.requests.UserPasswordRecoveryEmail;
import com.spring.eshop.service.implementations.user.requests.UserRegistration;
import com.spring.eshop.service.implementations.user.requests.UserResendActivationEmail;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserRequests implements com.spring.eshop.service.interfaces.IUserRequests {

	private final ConfirmationTokenDAO confirmationTokenDAO;
	private final IUserService userService;
	private final AuthGroupService authGroupService;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public UserRequests(ConfirmationTokenDAO confirmationTokenDAO, IUserService userService, AuthGroupService authGroupService, ApplicationEventPublisher publisher) {
		this.confirmationTokenDAO = confirmationTokenDAO;
		this.userService = userService;
		this.authGroupService = authGroupService;
		this.publisher = publisher;
	}

	@Override
	public void register(User user, UserInfo userInfo) {
		UserRegistration userRegistration = new UserRegistration(publisher, userService, authGroupService);
		userRegistration.execute(user, userInfo);
	}

	@Override
	@Transactional
	public void confirmRegistration(String token) {
		ConfirmUserRegistration confirmUserRegistration = new ConfirmUserRegistration(confirmationTokenDAO, userService);
		confirmUserRegistration.execute(token, null);
	}

	@Override
	public void resendActivationEmail(String username, String email) {
		UserResendActivationEmail resendActivationEmail = new UserResendActivationEmail(publisher);
		User user = userService.getUserByUsernameAndEmail(username, email);
		resendActivationEmail.execute(user, user.getUserInfo());
	}

	@Override
	public void passwordRecoveryEmail(String username, String email) {
		UserPasswordRecoveryEmail passwordRecoveryEmail = new UserPasswordRecoveryEmail(publisher);
		User user = userService.getUserByUsernameAndEmail(username, email);
		passwordRecoveryEmail.execute(user, user.getUserInfo());
	}

	@Override
	@Transactional
	public void confirmPasswordChange(String token, String password) {
		ConfirmChangePassword changePassword = new ConfirmChangePassword(confirmationTokenDAO, userService);
		changePassword.execute(token, password);
	}
}
