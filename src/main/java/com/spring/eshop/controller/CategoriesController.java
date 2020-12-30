package com.spring.eshop.controller;

import com.spring.eshop.service.interfaces.ICategoryService;
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

	private final ICategoryService categoryService;

	@Autowired
	public CategoriesController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public String getCategories(Pageable pageable, Model model) {
		model.addAttribute("categories", categoryService.getCategories(pageable));
		return "categories";
	}

	@GetMapping("/{id}")
	public String getProductsByCategory(@PathVariable int id, Pageable pageable, Model model) {
		model.addAttribute("products", categoryService.getProductsOfCategory(id, pageable));
		return "products";
	}
}
