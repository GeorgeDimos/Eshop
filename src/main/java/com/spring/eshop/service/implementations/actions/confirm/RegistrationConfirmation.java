package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import org.springframework.stereotype.Service;

@Service
public class RegistrationConfirmation extends ConfirmAction {

	public RegistrationConfirmation(UserDAO userDAO, ConfirmationTokenDAO confirmationTokenDAO) {
		super(userDAO, confirmationTokenDAO);
	}

	@Override
	protected void action(User user, String password) {
		user.setEnabled(true);
	}
}
