package com.spring.eshop.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStockInOrder( NotEnoughStockException ex, RedirectAttributes redirectAttrs ) {
		redirectAttrs.addFlashAttribute("notEnoughStockError",ex.getProductName());
		return "redirect:/cart";
	}
}
