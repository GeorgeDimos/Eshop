package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final ProductService productService;

	private final ShoppingCart shoppingCart;

	@Autowired
	public CheckoutController(ProductService productService, ShoppingCart shoppingCart) {
		this.productService = productService;
		this.shoppingCart = shoppingCart;
	}

	@GetMapping
	public String checkout(Model model) {
		model.addAttribute("shoppingList", shoppingCart.getShoppingCart());
		return "/checkout";
	}

	@PostMapping
	public String buyProducts(@RequestParam(required = false) String confirm) {
		if (confirm != null && confirm.equals("Purchase")) {
			productService.buyItems(shoppingCart.getShoppingCart());
		}

		return "redirect:/products";
	}
}