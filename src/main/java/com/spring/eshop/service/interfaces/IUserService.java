package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;

import javax.transaction.Transactional;

public interface IUserService {

	User getUserById(int id);

	User getUserByUsernameAndEmail(String username, String email) throws InvalidUserInfoException;

	boolean usernameInUse(String username);

	boolean emailInUse(String email);

	@Transactional
	void createUser(User user, UserInfo userInfo);

	@Transactional
	void encodePassword(User user, String password);

	@Transactional
	void enableUser(User user);
}
