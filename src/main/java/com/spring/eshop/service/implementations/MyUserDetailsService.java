package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.securingweb.UserPrinciple;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserDAO userDAO;
	private final AuthGroupDAO authGroupDAO;

	public MyUserDetailsService(UserDAO userDAO, AuthGroupDAO authGroupDAO) {
		this.userDAO = userDAO;
		this.authGroupDAO = authGroupDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userDAO.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Can not find user with username: " + username);
		}
		List<AuthGroup> authGroups = authGroupDAO.findByUsername(username);
		return new UserPrinciple(user.get(), authGroups);
	}
}
