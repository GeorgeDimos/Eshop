package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private final ShoppingCart shoppingCart;

	@Autowired
	public LoginController(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@GetMapping(value = "/login")
	public String login(HttpSession session) {
		if (session.getAttribute("user_id") != null) {
			return "redirect:/user";
		}
		return "login";
	}

	@GetMapping(value = "/successful-login")
	public String success(@AuthenticationPrincipal UserPrinciple userPrinciple, HttpSession session) {
		session.setAttribute("user_id", userPrinciple.getUserId());
		if (!shoppingCart.getShoppingCart().isEmpty()) {
			return "redirect:/cart";
		}
		return "redirect:/products";
	}

}
