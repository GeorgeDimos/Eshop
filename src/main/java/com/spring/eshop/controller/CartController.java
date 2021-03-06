package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final IProductService productService;
	private final ShoppingCart shoppingCart;

	@Autowired
	public CartController(IProductService productService, ShoppingCart shoppingCart) {
		this.productService = productService;
		this.shoppingCart = shoppingCart;
	}

	@GetMapping
	public String goToCart(Model model) {
		model.addAttribute("shoppingCart", shoppingCart);
		return "cart";
	}

	@PostMapping
	public String editCart(@RequestParam int id) {
		shoppingCart.remove(productService.getProduct(id));
		return "redirect:/cart";
	}
}
