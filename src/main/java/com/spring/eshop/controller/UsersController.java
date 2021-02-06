package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			Model model
	) {
		model.addAttribute("user", userPrinciple.getUser());
		return "profile";
	}

	@GetMapping("/confirmAction")
	public String deleteAccount() {
		return "confirmAction";
	}

	@PostMapping("/confirmAction")
	public String deleteAccount(@RequestParam(required = false) String confirm,
								@AuthenticationPrincipal UserPrinciple userPrinciple) {
		if (confirm != null) {
			userService.deleteUser(userPrinciple.getUser());
			return "redirect:/logout";
		}
		return "redirect:/user";
	}

	@GetMapping("/orders")
	public String getOrdersList(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			Model model,
			Pageable pageable
	) {
		User user = userPrinciple.getUser();
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByUser(user, pageable));
		return "orders";
	}

	@GetMapping("/orders/{oid}")
	public String getOrderDetails(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			@PathVariable int oid,
			Model model
	) {
		User user = userPrinciple.getUser();
		model.addAttribute("order", orderService.getOrder(user, oid));
		return "order";
	}

}
