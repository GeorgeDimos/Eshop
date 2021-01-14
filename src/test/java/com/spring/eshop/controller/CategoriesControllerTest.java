package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.ICategoryService;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriesController.class)
class CategoriesControllerTest {

	@MockBean
	ICategoryService categoryService;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	IUserService userService;

	Page<Category> pageCategories;
	Page<Product> pageProducts;
	Category c1;

	@BeforeEach
	void setUp() {
		c1 = new Category(1, "test_category", null);
		Product product = new Product(1,
				"test_product_1",
				"test_description_1",
				10, 10, List.of(), c1);
		Product p2 = new Product(2,
				"test_product_2",
				"test_description_2",
				10, 0.10, null, c1);
		c1.setProducts(List.of(product, p2));
		pageProducts = new PageImpl<>(List.of(product, p2));
		pageCategories = new PageImpl<>(List.of(c1));
	}

	@Test
	void getCategories() throws Exception {
		given(categoryService.getCategories(any())).willReturn(pageCategories);

		mockMvc.perform(get("/categories"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("categories"))
				.andExpect(view().name("categories"));
	}

	@Test
	void getProductsByCategory() throws Exception {

		given(categoryService.getProductsOfCategory(gt(0), any())).willReturn(pageProducts);

		mockMvc.perform(get("/categories/{id}", c1.getId()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("products"))
				.andExpect(view().name("products"));
	}
}