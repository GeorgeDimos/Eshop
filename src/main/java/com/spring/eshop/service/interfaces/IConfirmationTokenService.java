package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;

import javax.transaction.Transactional;

public interface IConfirmationTokenService {
	@Transactional
	String createConfirmationToken(User user);

	boolean isValid(String token);
}
