package com.spring.eshop.entity.validators;

import com.spring.eshop.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

	private UserDAO userDAO;

	@Autowired
	public UsernameValidator(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {

		return userDAO.findByUsername(name) == null;
	}
}
