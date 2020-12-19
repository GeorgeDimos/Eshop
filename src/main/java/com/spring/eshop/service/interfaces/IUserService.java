package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.actions.confirm.ConfirmAction;
import com.spring.eshop.service.implementations.actions.request.RequestAction;

import javax.transaction.Transactional;

public interface IUserService {

	User getUserById(int id);

	User getUserByUsernameAndEmail(String username, String email) throws InvalidUserInfoException;

	void request(RequestAction template);

	@Transactional
	void confirm(ConfirmAction template);
}
