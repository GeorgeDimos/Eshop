package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
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

	public ConfirmationToken getByToken(String token) throws NoSuchElementException {
		return confirmationTokenDAO.findByToken(token).orElseThrow(NoSuchElementException::new);
	}

	public User getUserFromToken(String token) throws NoSuchElementException {
		ConfirmationToken confirmationToken = confirmationTokenDAO.findByToken(token).orElseThrow(NoSuchElementException::new);
		return confirmationToken.getUser();
	}

	public void deleteByToken(String token) {
		confirmationTokenDAO.deleteByToken(token);
	}
}
