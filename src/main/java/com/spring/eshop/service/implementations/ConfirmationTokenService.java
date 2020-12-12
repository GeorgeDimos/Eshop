package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public ConfirmationTokenService(ConfirmationTokenDAO confirmationTokenDAO) {
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	@Transactional
	public String createAndReturnConfirmationToken(User user) {
		ConfirmationToken token = new ConfirmationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUser(user);
		confirmationTokenDAO.save(token);
		return token.getToken();
	}
}
