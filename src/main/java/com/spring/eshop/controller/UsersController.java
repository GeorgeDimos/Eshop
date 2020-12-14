package com.spring.eshop.controller;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String profile(@PathVariable String id, Model model) {
		User user = userService.getUserById(Integer.parseInt(id));
		model.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/orders")
	public String getOrdersList(@PathVariable int id,
								Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(@PathVariable int id, @PathVariable int oid, Model model) {
		Order order = orderService.getOrder(id, oid);
		if (order == null) {
			return "redirect:/error";
		}
		model.addAttribute("order", order);
		return "order";
	}
}
