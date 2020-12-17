package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;

import javax.transaction.Transactional;

public interface IUserRequests {
	void register(User user, UserInfo userInfo);

	@Transactional
	void confirmRegistration(String token);

	void resendActivationEmail(String username, String email);

	void passwordRecoveryEmail(String username, String email);

	@Transactional
	void confirmPasswordChange(String token, String password);
}
