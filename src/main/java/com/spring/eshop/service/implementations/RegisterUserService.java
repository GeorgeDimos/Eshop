package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.dao.UserDetailsDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserDetails;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
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

		if(userDAO.findByUsername(user.getUsername()).isPresent()){
			throw new UserAlreadyExistsException("Username "+ user.getUsername() + " already exists");
		}

		if(userDetailsDAO.findByEmail(userDetails.getEmail()).isPresent()){
			throw new UserAlreadyExistsException("Email "+ userDetails.getEmail() + " already exists");
		}

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
