package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ShoppingCart shoppingCart;

	@GetMapping
	public String getProductsTest(Pageable pageable, Model model) {
		model.addAttribute("products", productService.getItems(pageable));
		return "products";
	}

	@GetMapping("/{id}")
	public String getProduct(@PathVariable int id, Model model) {
		model.addAttribute("product", productService.getItem(id));
		return "productPage";
	}

	@PostMapping("/{id}")
	public String addProductToCart(@PathVariable int id, @RequestParam int quantity) {
		shoppingCart.add(productService.getItem(id), quantity);
		return "redirect:/products";
	}
}
