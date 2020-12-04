package com.spring.eshop.securingweb;

import java.util.List;

import com.spring.eshop.dao.AuthGroupdDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserDAO userDAO;
	private AuthGroupdDAO authGroupdDAO;

	public MyUserDetailsService(UserDAO userDAO, AuthGroupdDAO authGroupdDAO) {
		this.userDAO = userDAO;
		this.authGroupdDAO = authGroupdDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userDAO.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Can not find user with username: " + username);
		}
		List<AuthGroup> authGroups = authGroupdDAO.findByUsername(username);
		return new UserPrinciple(user, authGroups);
	}

}
