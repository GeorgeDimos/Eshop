package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.actions.confirm.ConfirmAction;
import com.spring.eshop.service.implementations.actions.request.RequestAction;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements IUserService {
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenDAO confirmationTokenDAO;
	private final ApplicationEventPublisher publisher;
	private final AuthGroupService authGroupService;

	@Autowired
	public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder, ConfirmationTokenDAO confirmationTokenDAO, ApplicationEventPublisher publisher, AuthGroupService authGroupService) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
		this.confirmationTokenDAO = confirmationTokenDAO;
		this.publisher = publisher;
		this.authGroupService = authGroupService;
	}

	@Override
	public User getUserById(int id) {
		return userDAO.findById(id).orElseThrow();
	}

	@Override
	public User getUserByUsernameAndEmail(String username, String email) throws InvalidUserInfoException {

		return userDAO.findByUsernameAndUserInfoEmail(username, email).
				orElseThrow(() -> new InvalidUserInfoException("Invalid user info"));
	}

	@Override
	public void request(RequestAction request) {
		publisher.publishEvent(request.execute(userDAO, authGroupService, passwordEncoder));
	}

	@Override
	@Transactional
	public void confirm(ConfirmAction action) {
		action.execute(userDAO, confirmationTokenDAO, passwordEncoder);
	}
}
