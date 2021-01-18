package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	private final ShoppingCart shoppingCart;

	@Autowired
	public LoginController(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/successful-login")
	public String success() {
		if (!shoppingCart.getShoppingCart().isEmpty()) {
			return "redirect:/cart";
		}
		return "redirect:/products";
	}

}
