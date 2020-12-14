package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserService {
	private final UserDAO userDAO;
	private final UserInfoDAO userInfoDAO;
	private final AuthGroupService authGroupService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserDAO userDAO, UserInfoDAO userInfoDAO, AuthGroupService authGroupService, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.userInfoDAO = userInfoDAO;
		this.authGroupService = authGroupService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createUser(User user, UserInfo userInfo) throws UserAlreadyExistsException {
		if (userDAO.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
		}

		if (userInfoDAO.findByEmail(userInfo.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Email " + userInfo.getEmail() + " already exists");
		}

		user.setEnabled(false);
		this.changePassword(user, user.getPassword());

		authGroupService.createAuthGroupForUser(user.getUsername());

		userInfo.setUser(user);
		userInfoDAO.save(userInfo);
	}

	@Transactional
	public void changePassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		userDAO.save(user);
	}

	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userDAO.save(user);
	}

	public User getUserById(int id) throws NoSuchElementException {
		return userDAO.findById(id).orElseThrow(NoSuchElementException::new);
	}

	public User getUserByUsernameAndEmail(String username, String email) {
		User user = userDAO.findByUsername(username).orElseThrow(() -> new InvalidUserInfoException("Can't find username"));

		if (!user.getUserInfo().getEmail().equals(email)) {
			throw new InvalidUserInfoException("Wrong email");
		}

		if (!user.getEnabled()) {
			throw new InvalidUserInfoException("You need to confirm your account first.");
		}

		return user;
	}
}
