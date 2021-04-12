package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	private final UserDAO userDAO;

	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void delete(User user) {
		userDAO.delete(user);
	}

	@Override
	public User loadUserByUsername(String username) {
		return userDAO.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("Can not find user with username: " + username));
	}
}
