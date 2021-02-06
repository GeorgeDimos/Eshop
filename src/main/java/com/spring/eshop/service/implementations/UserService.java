package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

	private final UserDAO userDAO;

	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User getUserById(int id) {
		return userDAO.findById(id).orElseThrow();
	}

	@Override
	public void deleteUser(User user) {
		userDAO.delete(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userDAO.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Can not find user with username: " + username);
		}

		List<AuthGroup> authGroups = List.of(user.get().getAuthGroup());
		return new UserPrinciple(user.get(), authGroups);
	}
}
