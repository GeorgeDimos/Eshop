package com.spring.eshop.controller;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profiles/{id}")
public class ProfileController {

	private final OrderDAO orderDAO;

	private final UserDAO userDAO;

	@Autowired
	public ProfileController(OrderDAO orderDAO, UserDAO userDAO) {
		this.orderDAO = orderDAO;
		this.userDAO = userDAO;
	}

	@GetMapping
	public String profile(@PathVariable String id, Model model) {
		User user = userDAO.findById(Integer.valueOf(id)).orElseThrow();
		model.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/orders")
	public String getOrdersList(@PathVariable int id,
								Model model) {
		User user = userDAO.findById(id).orElseThrow(null);
		model.addAttribute("user", user);
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(@PathVariable int oid, Model model) {
		Order order = orderDAO.findById(oid).orElseThrow(null);
		model.addAttribute("order", order);
		return "order";
	}
}
