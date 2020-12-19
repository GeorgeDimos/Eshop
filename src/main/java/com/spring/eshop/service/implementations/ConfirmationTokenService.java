package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService {

	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public ConfirmationTokenService(ConfirmationTokenDAO confirmationTokenDAO) {
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	@Override
	@Transactional
	public String createConfirmationToken(User user) {

		//clear out previous token
		confirmationTokenDAO.deleteByUser(user);

		ConfirmationToken token = new ConfirmationToken();
		//token.setToken(UUID.randomUUID().toString());
		token.setUser(user);
		confirmationTokenDAO.save(token);
		return token.getToken();
	}

	@Override
	public boolean isValid(String token) {
		return confirmationTokenDAO.existsByToken(token);
	}
}
