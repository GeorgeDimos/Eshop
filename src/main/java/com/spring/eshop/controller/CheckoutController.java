package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.actions.OrderRegistration;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final ShoppingCart shoppingCart;
	private final IUserService userService;
	private final OrderRegistration orderRegistration;

	@Autowired
	public CheckoutController(ShoppingCart shoppingCart, IUserService userService, OrderRegistration orderRegistration) {
		this.shoppingCart = shoppingCart;
		this.userService = userService;
		this.orderRegistration = orderRegistration;
	}

	@GetMapping
	public String checkout(Model model) {
		model.addAttribute("shoppingList", shoppingCart.getShoppingCart());
		return "checkout";
	}

	@PostMapping
	public String buyProducts(@SessionAttribute int user_id, @RequestParam(required = false) String confirm) {
		if (confirm != null && !shoppingCart.isEmpty()) {
			User user = userService.getUserById(user_id);
			orderRegistration.execute(user, shoppingCart.getShoppingCart());
			shoppingCart.clear();
			return "redirect:/user/orders";
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
