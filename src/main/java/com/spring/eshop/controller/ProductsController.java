package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {

	private final IProductService productService;
	private final ShoppingCart shoppingCart;

	@Autowired
	public ProductsController(IProductService productService, ShoppingCart shoppingCart) {
		this.productService = productService;
		this.shoppingCart = shoppingCart;
	}

	@GetMapping
	public String getProducts(Pageable pageable, Model model) {
		model.addAttribute("products", productService.getProducts(pageable));
		return "products";
	}

	@GetMapping("/{id}")
	public String getProduct(@PathVariable int id, Model model) {
		model.addAttribute("product", productService.getProduct(id));
		return "productPage";
	}

	@PostMapping("/{id}")
	public String addProductToCart(@PathVariable int id,
								   @RequestParam int quantity,
								   @RequestParam(required = false) String goToCart) {
		shoppingCart.add(productService.getProduct(id), quantity);
		if (goToCart != null) {
			return "redirect:/cart";
		}
		return "redirect:/products";
	}
}
