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

import java.util.Optional;

@Controller
@RequestMapping("profiles/{pid}/orders")
public class OrdersController {

	private final OrderDAO orderDAO;

	private final UserDAO userDAO;

	@Autowired
	public OrdersController(OrderDAO orderDAO, UserDAO userDAO) {
		this.orderDAO = orderDAO;
		this.userDAO = userDAO;
	}

	@GetMapping
	public String getOrdersList(@PathVariable int pid,
								Model model) {
		Optional<User> user = userDAO.findById(pid);
		model.addAttribute("user", user.orElseThrow(null));
		return "orders";
	}

	@GetMapping("/{oid}")
	public String getOrderDetails(@PathVariable int oid, Model model) {
		Optional<Order> order = orderDAO.findById(oid);
		model.addAttribute("order", order.orElseThrow(null));
		return "order";
	}
}
