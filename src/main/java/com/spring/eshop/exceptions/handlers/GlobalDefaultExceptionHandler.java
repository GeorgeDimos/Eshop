package com.spring.eshop.exceptions.handlers;

import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStockInOrder(NotEnoughStockException ex, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("notEnoughStockError", ex.getProductName());
		return "redirect:/cart";
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	private String userAlreadyExists(UserAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("userAlreadyExists", ex.getMessage());
		return "redirect:/register";
	}
}
