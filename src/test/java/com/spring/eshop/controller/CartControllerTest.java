package com.spring.eshop.controller;

import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

	@MockBean
	IProductService productService;
	@MockBean
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	@Captor
	ArgumentCaptor<Product> productCaptor;

	@Test
	void goToCart() throws Exception {

		mockMvc.perform(get("/cart"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("shoppingCart", shoppingCart))
				.andExpect(view().name("cart"));
	}

	@Test
	void editCart() throws Exception {
		Product product = mock(Product.class);
		given(productService.getProduct(anyInt())).willReturn(product);

		mockMvc.perform(post("/cart")
						.param("id", String.valueOf(product.getId()))
						.with(csrf())
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"));

		Mockito.verify(shoppingCart).remove(productCaptor.capture());
		assertEquals(product, productCaptor.getValue());

		then(shoppingCart).should().remove(product);
	}
}