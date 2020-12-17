package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/{id}")
public class UsersController {

	private final IUserService userService;
	private final IOrderService orderService;

	@Autowired
	public UsersController(IUserService userService, IOrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping
	public String profile(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "profile";
	}

	@GetMapping("/orders")
	public String getOrdersList(@PathVariable int id,
								Model model,
								Pageable pageable) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByUser(user, pageable));
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(@PathVariable int id, @PathVariable int oid, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("order", orderService.getOrder(user, oid));
		return "order";
	}
}
