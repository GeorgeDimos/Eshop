package com.spring.eshop.controller;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.implementations.actions.ActionService;
import com.spring.eshop.service.implementations.actions.request.OrderRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final ShoppingCart shoppingCart;
	private final AuthGroupService authGroupService;
	private final ActionService actionService;

	@Autowired
	public CheckoutController(ShoppingCart shoppingCart, AuthGroupService authGroupService, ActionService actionService) {
		this.shoppingCart = shoppingCart;
		this.authGroupService = authGroupService;
		this.actionService = actionService;
	}

	@GetMapping
	public String checkout(Model model) {
		model.addAttribute("shoppingList", shoppingCart.getShoppingCart());
		return "checkout";
	}

	@PostMapping
	public String buyProducts(@RequestParam(required = false) String confirm) {
		if (confirm != null) {
			actionService.register(new OrderRegistration(new Order(), authGroupService.getCurrentUser(), shoppingCart.getShoppingCart()));
			shoppingCart.clear();
		}

		return "redirect:/products";
	}

	@ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStockInOrder(NotEnoughStockException ex, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("notEnoughStockError",
				"We don't have enough stock of " + ex.getProductName() + " anymore. Please reduce the quantity or remove it.");
		return "redirect:/cart";
	}
}
