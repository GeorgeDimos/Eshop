package com.spring.eshop.controller;

import com.spring.eshop.service.interfaces.ICategoryService;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

	private final ICategoryService ICategoryService;
	private final IProductService productService;

	@Autowired
	public CategoriesController(ICategoryService ICategoryService, IProductService productService) {
		this.ICategoryService = ICategoryService;
		this.productService = productService;
	}

	@GetMapping
	public String getCategories(Pageable pageable, Model model) {
		model.addAttribute("categories", ICategoryService.getItems(pageable));
		return "categories";
	}

	@GetMapping("/{id}")
	public String getProductByCategories(@PathVariable int id, Pageable pageable, Model model) {
		model.addAttribute("products", productService.getProductsByCategory(id, pageable));
		return "products";
	}
}
