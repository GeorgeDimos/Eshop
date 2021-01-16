package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UsersController {

	private final IUserService userService;
	private final IOrderService orderService;

	@Autowired
	public UsersController(IUserService userService, IOrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping
	public String profile(@SessionAttribute int user_id, Model model) {
		model.addAttribute("user", userService.getUserById(user_id));
		return "profile";
	}

	@GetMapping("/orders")
	public String getOrdersList(@SessionAttribute int user_id,
								Model model,
								Pageable pageable) {
		User user = userService.getUserById(user_id);
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByUser(user, pageable));
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(@SessionAttribute int user_id, @PathVariable int oid, Model model) {
		User user = userService.getUserById(user_id);
		model.addAttribute("order", orderService.getOrder(user, oid));
		return "order";
	}

	@ExceptionHandler(ServletRequestBindingException.class)
	public String noUserId(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("unauthenticated", "You need to be signed in to access that page.");
		return "redirect:/login";
	}
}
