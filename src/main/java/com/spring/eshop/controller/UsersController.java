package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UsersController {

	private final IOrderService orderService;

	@Autowired
	public UsersController(IOrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public String profile(
			@AuthenticationPrincipal UserPrinciple userPrinciple,
			Model model
	) {
		model.addAttribute("user", userPrinciple.getUser());
		return "profile";
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

	@ExceptionHandler(NullPointerException.class)
	public String noUserId(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("unauthenticated", "You need to be signed in to access that page.");
		return "redirect:/login";
	}
}
