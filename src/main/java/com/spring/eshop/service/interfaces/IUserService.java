package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.exceptions.UserAlreadyExistsException;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

public interface IUserService {
	@Transactional
	void createUser(User user, UserInfo userInfo) throws UserAlreadyExistsException;

	User getUserById(int id) throws NoSuchElementException;

	User getUserByUsernameAndEmail(String username, String email) throws InvalidUserInfoException;

	@Transactional
	void changePassword(User user, String password);

	@Transactional
	void enableUser(User user);
}
