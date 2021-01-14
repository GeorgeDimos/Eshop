package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

	@MockBean
	IProductService productService;
	@MockBean
	ShoppingCart shoppingCart;
	@MockBean
	IUserService userService;

	@Autowired
	MockMvc mockMvc;

	Map<Product, Integer> cart;
	Product product;

	@BeforeEach
	void setUp() {
		Category c1 = new Category(1, "test_category", null);
		product = new Product(1,
				"test_product_1",
				"test_description_1",
				10, 10, List.of(), c1);
		Product product2 = new Product(4,
				"test_product_4",
				"test_description_4",
				15, 1.1, List.of(), c1);
		cart = Map.of(product, product.getId(), product2, product2.getId());
	}

	@Test
	void goToCart() throws Exception {
		given(shoppingCart.getShoppingCart()).willReturn(cart);
		mockMvc.perform(get("/cart"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("shoppingList", any(cart.getClass())))
				.andExpect(view().name("cart"));
	}

	@Test
	void editCart() throws Exception {
		given(productService.getProduct(anyInt())).willReturn(product);

		mockMvc.perform(post("/cart").param("id", String.valueOf(product.getId())))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"));

		then(shoppingCart).should().remove(product);
	}
}