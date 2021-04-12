package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

	@MockBean
	IProductService productService;

	@Autowired
	MockMvc mockMvc;

	Page<Product> page;

	@BeforeEach
	void setUp() {
		Category c1 = mock(Category.class);
		page = new PageImpl<>(List.of(
				new Product(1,
						"test_product_1",
						"test_description_1",
						10, 10, List.of(), c1),
				new Product(2,
						"test_product_2",
						"test_description_2",
						10, 0.10, null, c1)
		));
	}

	@Test
	void searchProductByName() throws Exception {
		given(productService.getProductsByName(anyString(), any(Pageable.class))).willReturn(page);

		mockMvc.perform(get("/search").param("name", "product"))
				.andExpect(status().isOk())
				.andExpect(view().name("products"))
				.andExpect(model().attributeExists("products"));
	}
}