package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UsersController {

	private final IOrderService orderService;
	private final IUserService userService;

	@Autowired
	public UsersController(IOrderService orderService, IUserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@GetMapping
	public String profile(
					@AuthenticationPrincipal User user,
					Model model
	) {
		model.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/deleteAccount")
	public String deleteAccount() {
		return "deleteAccount";
	}

	@PostMapping("/deleteAccount")
	public String deleteAccount(@RequestParam(required = false) String confirm,
															@AuthenticationPrincipal User user) {
		if (confirm != null) {
			userService.delete(user);

			SecurityContextHolder.clearContext();

			return "redirect:/login?logout";
		}
		return "redirect:/user";
	}

	@GetMapping("/orders")
	public String getOrdersList(
					@AuthenticationPrincipal User user,
					Model model,
					Pageable pageable
	) {
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByUser(user, pageable));
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(
					@AuthenticationPrincipal User user,
					@PathVariable int oid,
					Model model
	) {
		model.addAttribute("order", orderService.getOrder(user, oid));
		return "order";
	}

}
