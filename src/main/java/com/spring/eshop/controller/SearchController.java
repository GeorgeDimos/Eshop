package com.spring.eshop.controller;

import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

	private final IProductService productService;

	@Autowired
	public SearchController(IProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/search")
	public String searchProductByName(@RequestParam("name") String name, Pageable pageable, Model model) {
		model.addAttribute("products", productService.getProductsByName(name, pageable));
		return "products";
	}
}
