package com.spring.eshop.exceptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler({NoSuchElementException.class
			, PropertyReferenceException.class
			, InvalidDataAccessResourceUsageException.class})
	private String invalidURL(RuntimeException ex) {
		logger.error(ex.getMessage());
		return "redirect:/error";
	}
}
