package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private final IUserService userService;
	private final ShoppingCart shoppingCart;

	@Autowired
	public LoginController(IUserService userService, ShoppingCart shoppingCart) {
		this.userService = userService;
		this.shoppingCart = shoppingCart;
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/successful-login")
	public String success(HttpSession session) {
		session.setAttribute("user_id", userService.getCurrentUser().getId());
		if (!shoppingCart.getShoppingCart().isEmpty()) {
			return "forward:/cart";
		}
		return "forward:/products";
	}

}
