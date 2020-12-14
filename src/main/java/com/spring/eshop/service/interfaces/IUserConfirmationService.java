package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.UserAlreadyExistsException;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

public interface IUserConfirmationService {
	@Transactional
	void registerUser(User user, UserInfo userInfo) throws UserAlreadyExistsException;

	@Transactional
	User confirmUserRegistration(String token) throws NoSuchElementException;

	void sendPasswordRecoveryEmail(String username, String email);

	@Transactional
	void changePassword(String token, String password) throws NoSuchElementException;
}
