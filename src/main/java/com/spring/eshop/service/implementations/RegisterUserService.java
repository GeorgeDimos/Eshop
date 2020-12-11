package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserInfoDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

	private final UserDAO userDAO;

	private final UserInfoDAO userInfoDAO;

	private final PasswordEncoder passwordEncoder;

	private final AuthGroupDAO authGroupDAO;

	@Autowired
	public RegisterUserService(UserDAO userDAO, UserInfoDAO userInfoDAO, PasswordEncoder passwordEncoder, AuthGroupDAO authGroupDAO) {
		this.userDAO = userDAO;
		this.userInfoDAO = userInfoDAO;
		this.passwordEncoder = passwordEncoder;
		this.authGroupDAO = authGroupDAO;
	}

	public void registerNewUser(User user, UserInfo userDetails) {

		if (userDAO.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
		}

		if (userInfoDAO.findByEmail(userDetails.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Email " + userDetails.getEmail() + " already exists");
		}

		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.save(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUsername(user.getUsername());
		authGroup.setAuthority("user");
		authGroupDAO.save(authGroup);

		userDetails.setUser(user);
		userInfoDAO.save(userDetails);
	}
}
