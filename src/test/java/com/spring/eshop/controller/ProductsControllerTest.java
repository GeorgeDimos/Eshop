package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Image;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

	@MockBean
	IProductService productService;

	@SpyBean
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	Category c1 = mock(Category.class);
	Product product = new Product(1,
					"test_product_1",
					"test_description_1",
					10, 10, List.of(new Image()), c1);

	@AfterEach
	void tearDown() {
		shoppingCart.clear();
	}

	@Test
	void getProducts() throws Exception {

		Page<Product> pageProducts = new PageImpl<>(List.of(
						product,
						new Product(
										2, "test_product_2",
										"test_description_2",
						10, 0.10, null, c1
				)));
		given(productService.getProducts(any(Pageable.class))).willReturn(pageProducts);

		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(view().name("products"))
				.andExpect(model().attributeExists("products"));
	}

	@Test
	void getProduct() throws Exception {
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(get("/products/{id}", product.getId()))
						.andExpect(status().isOk())
						.andExpect(view().name("productPage"))
						.andExpect(model().attributeExists("product"));
	}

	@Test
	void getInvalidProduct() throws Exception {
		given(productService.getProduct(anyInt())).willThrow(new NoSuchElementException());
		mockMvc.perform(get("/products/{id}", -1))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/error"));
	}

	@Test
	void addProductToCartGoToProducts() throws Exception {
		int quantity = 2;
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(post("/products/{id}", product.getId())
						.param("quantity", String.valueOf(quantity))
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/products"));

		then(shoppingCart).should().add(product, quantity);
		assertTrue(shoppingCart.getItemsList().containsKey(product));
		assertEquals(quantity, shoppingCart.getItemsList().get(product).intValue());
	}

	@Test
	void addProductToCartGoToCart() throws Exception {
		int quantity = 2;
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(post("/products/{id}", product.getId())
						.param("quantity", String.valueOf(quantity))
						.param("goToCart", "Ok")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/cart"));

		then(shoppingCart).should().add(product, quantity);
		assertTrue(shoppingCart.getItemsList().containsKey(product));
		assertEquals(quantity, shoppingCart.getItemsList().get(product).intValue());
	}
}