package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.interfaces.IOrderRegistration;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String buyProducts(@RequestParam(required = false) String confirm,
														RedirectAttributes flashAttributes) {
		if (confirm != null && !shoppingCart.isEmpty()) {
			flashAttributes.addFlashAttribute("order", orderRegistration.execute(shoppingCart.getItemsList()));
			shoppingCart.clear();
			return "redirect:/checkout/order";
		}

		return "redirect:/products";
	}

	@GetMapping("/order")
	public String order(Model model){
		if(model.containsAttribute("order"))
			return "order";
		return "redirect:/home";
	}


	@ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStockInOrder(NotEnoughStockException ex, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("notEnoughStockError",
				"We don't have enough stock of " + ex.getProductName() + " anymore. Please reduce the quantity or remove it.");
		return "redirect:/cart";
	}
}
