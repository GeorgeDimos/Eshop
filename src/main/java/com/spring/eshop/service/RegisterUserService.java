package com.spring.eshop.service;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserDetailsDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

	private final UserDAO userDAO;

	private final UserDetailsDAO userDetailsDAO;

	private final PasswordEncoder passwordEncoder;

	private final AuthGroupDAO authGroupDAO;

	@Autowired
	public RegisterUserService(UserDAO userDAO, UserDetailsDAO userDetailsDAO, PasswordEncoder passwordEncoder, AuthGroupDAO authGroupDAO) {
		this.userDAO = userDAO;
		this.userDetailsDAO = userDetailsDAO;
		this.passwordEncoder = passwordEncoder;
		this.authGroupDAO = authGroupDAO;
	}

	public void registerNewUser(User user, UserDetails userDetails) {
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.save(user);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setUsername(user.getUsername());
		authGroup.setAuthority("user");
		authGroupDAO.save(authGroup);

		userDetails.setUser(user);
		userDetailsDAO.save(userDetails);
	}
}
