package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.interfaces.IOrderRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final ShoppingCart shoppingCart;
	private final IOrderRegistration orderRegistration;

	@Autowired
	public CheckoutController(ShoppingCart shoppingCart, IOrderRegistration orderRegistration) {
		this.shoppingCart = shoppingCart;
		this.orderRegistration = orderRegistration;
	}

	@GetMapping
	public String checkout(Model model) {
		model.addAttribute("shoppingCart", shoppingCart);
		return "checkout";
	}

	@PostMapping
	public String buyProducts(@AuthenticationPrincipal User user,
														@RequestParam(required = false) String confirm) {
		if (confirm != null && !shoppingCart.isEmpty()) {
			int orderId = orderRegistration.execute(user, shoppingCart.getItemsList());
			shoppingCart.clear();
			return "redirect:/user/orders/" + orderId;
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
