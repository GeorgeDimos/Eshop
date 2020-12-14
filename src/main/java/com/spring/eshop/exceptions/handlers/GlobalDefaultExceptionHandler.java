package com.spring.eshop.exceptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler({Exception.class, RuntimeException.class})
	private String invalidURL(NoSuchElementException ex) {
		logger.info(ex.getMessage());
		return "redirect:/error";
	}
}
