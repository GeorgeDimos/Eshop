package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements IUserService {
	private final UserDAO userDAO;
	private final UserInfoDAO userInfoDAO;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserDAO userDAO, UserInfoDAO userInfoDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.userInfoDAO = userInfoDAO;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void createUser(User user, UserInfo userInfo) {

		encodePassword(user, user.getPassword());

		userInfo.setUser(user);
		userInfoDAO.save(userInfo);
	}

	@Override
	public User getUserById(int id) {
		return userDAO.findById(id).orElseThrow();
	}

	@Override
	public boolean userExists(String username, String email) {
		return userDAO.existsByUsernameAndUserInfoEmail(username, email);
	}

	@Override
	@Transactional
	public void encodePassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		userDAO.save(user);
	}

	@Override
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userDAO.save(user);
	}

	@Override
	public User getUserByUsernameAndEmail(String username, String email) throws InvalidUserInfoException {

		return userDAO.findByUsernameAndUserInfoEmail(username, email).
				orElseThrow(() -> new InvalidUserInfoException("Invalid user info"));
	}
}
