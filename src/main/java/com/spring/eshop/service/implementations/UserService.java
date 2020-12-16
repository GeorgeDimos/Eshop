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
	private final AuthGroupService authGroupService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserDAO userDAO, UserInfoDAO userInfoDAO, AuthGroupService authGroupService, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.userInfoDAO = userInfoDAO;
		this.authGroupService = authGroupService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void createUser(User user, UserInfo userInfo) {

		user.setEnabled(false);
		encodePassword(user, user.getPassword());

		authGroupService.createAuthGroupForUser(user.getUsername());

		userInfo.setUser(user);
		userInfoDAO.save(userInfo);
	}

	@Override
	public User getUserById(int id) {
		return userDAO.findById(id).orElseThrow();
	}

	@Override
	public User getUserByUsername(String username) {
		return userDAO.findByUsername(username).orElseThrow();
	}

	@Override
	public UserInfo getUserInfoByEmail(String email) {
		return userInfoDAO.findByEmail(email).orElseThrow();
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
		User user = userDAO.findByUsername(username).
				orElseThrow(() -> new InvalidUserInfoException("Can't find username"));

		if (!user.getUserInfo().getEmail().equals(email)) {
			throw new InvalidUserInfoException("Wrong email");
		}

		return user;
	}
}
