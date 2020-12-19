package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthGroupService implements UserDetailsService {

	private final UserDAO userDAO;
	private final AuthGroupDAO authGroupDAO;

	public AuthGroupService(UserDAO userDAO, AuthGroupDAO authGroupDAO) {
		this.userDAO = userDAO;
		this.authGroupDAO = authGroupDAO;
	}

	@Transactional
	public void createAuthGroupForUser(String username) {
		AuthGroup authGroup = new AuthGroup();
		authGroup.setUsername(username);
		authGroup.setAuthority("user");
		authGroupDAO.save(authGroup);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userDAO.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Can not find user with username: " + username);
		}
		List<AuthGroup> authGroups = authGroupDAO.findByUsername(user.get().getUsername());
		return new UserPrinciple(user.get(), authGroups);
	}

	public User getCurrentUser() {
		UserPrinciple currentUserPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return userDAO.findById(currentUserPrinciple.getUserId()).orElseThrow();
	}
}
