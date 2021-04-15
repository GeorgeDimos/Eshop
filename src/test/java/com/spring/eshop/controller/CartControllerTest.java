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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

	@MockBean
	IProductService productService;

	@SpyBean
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	@Captor
	ArgumentCaptor<Product> productCaptor;

	@Test
	void goToEmptyCart() throws Exception {

		mockMvc.perform(get("/cart"))
						.andExpect(status().isOk())
						.andExpect(model().attribute("shoppingCart", shoppingCart))
						.andExpect(view().name("cart"));
	}

	@Test
	void goToCartWithItems() throws Exception {
		shoppingCart.add(mock(Product.class), 1);
		shoppingCart.add(mock(Product.class), 2);
		mockMvc.perform(get("/cart"))
						.andExpect(status().isOk())
						.andExpect(model().attribute("shoppingCart", shoppingCart))
						.andExpect(view().name("cart"));
	}

	@Test
	void editCartProductInCart() throws Exception {
		Product product = mock(Product.class);
		given(productService.getProduct(gt(0))).willReturn(product);

		shoppingCart.add(product, 1);
		assertTrue(shoppingCart.getItemsList().containsKey(product));

		mockMvc.perform(post("/cart")
						.param("id", "1")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/cart"));

		Mockito.verify(shoppingCart).remove(productCaptor.capture());
		assertEquals(product, productCaptor.getValue());

		then(shoppingCart).should().remove(product);
		assertFalse(shoppingCart.getItemsList().containsKey(product));
	}

	@Test
	void editCartProductNotInCart() throws Exception {
		Product product = mock(Product.class);
		given(productService.getProduct(gt(0))).willReturn(product);

		assertFalse(shoppingCart.getItemsList().containsKey(product));

		mockMvc.perform(post("/cart")
						.param("id", "1")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/cart"));

		Mockito.verify(shoppingCart).remove(productCaptor.capture());
		assertEquals(product, productCaptor.getValue());

		then(shoppingCart).should().remove(product);
		assertFalse(shoppingCart.getItemsList().containsKey(product));
	}

	@Test
	void editCartInvalidProduct() throws Exception {
		given(productService.getProduct(gt(0))).willThrow(new NoSuchElementException());

		mockMvc.perform(post("/cart")
						.param("id", "1")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/error"));

		Mockito.verify(shoppingCart, never()).remove(any(Product.class));
	}
}