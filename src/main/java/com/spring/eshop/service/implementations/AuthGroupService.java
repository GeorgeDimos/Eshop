package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthGroupService implements UserDetailsService {

	private final UserDAO userDAO;
	private final AuthGroupDAO authGroupDAO;

	public AuthGroupService(UserDAO userDAO, AuthGroupDAO authGroupDAO) {
		this.userDAO = userDAO;
		this.authGroupDAO = authGroupDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userDAO.findByUsername(username).orElseThrow();

		List<AuthGroup> authGroups = authGroupDAO.findByUser(user);
		return new UserPrinciple(user, authGroups);
	}

	public User getCurrentUser() {
		UserPrinciple currentUserPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return userDAO.findById(currentUserPrinciple.getUserId()).orElseThrow();
	}
}
