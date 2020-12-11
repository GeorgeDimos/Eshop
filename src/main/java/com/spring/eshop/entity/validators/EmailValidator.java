package com.spring.eshop.entity.validators;

import com.spring.eshop.dao.UserDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private UserDetailsDAO userDetailsDAO;

	@Autowired
	public EmailValidator(UserDetailsDAO userDetailsDAO) {
		this.userDetailsDAO = userDetailsDAO;
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

		return userDetailsDAO.findByEmail(email) == null;
	}
}
