package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

public interface IConfirmationTokenService {
	@Transactional
	String createConfirmationToken(User user);

	ConfirmationToken getConfirmationTokenByToken(String token) throws NoSuchElementException;

	User getUserByToken(String token) throws NoSuchElementException;

	void deleteByToken(String token);
}
