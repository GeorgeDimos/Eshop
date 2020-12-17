package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.UserAlreadyExistsException;

import javax.transaction.Transactional;

public interface IUserConfirmationService {
	@Transactional
	void registerUser(User user, UserInfo userInfo) throws UserAlreadyExistsException;

	void resendActivationEmail(String username, String email);

	void sendPasswordRecoveryEmail(String username, String email);

	@Transactional
	void confirmPasswordChange(String token, String password);

	@Transactional
	void confirmUserRegistration(String token);
}
