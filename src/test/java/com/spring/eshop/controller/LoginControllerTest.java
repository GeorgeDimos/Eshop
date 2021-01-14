package com.spring.eshop.controller;

import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

	@MockBean
	IUserService userService;

	@MockBean
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	@Test
	void login() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	void successEmptyShoppingCart() throws Exception {
		doReturn(new User(1, "a", "b", true, null, null, null))
				.when(userService).getCurrentUser();

		doReturn(new HashMap<>()).
				when(shoppingCart).getShoppingCart();

		mockMvc.perform(get("/successful-login"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/products"));
	}

	@Test
	void successShoppingCartHasItems() throws Exception {
		doReturn(new User(1, "a", "b", true, null, null, null))
				.when(userService).getCurrentUser();

		Map<Product, Integer> map = new HashMap<>();
		map.put(new Product(), 4);
		doReturn(map).
				when(shoppingCart).getShoppingCart();

		mockMvc.perform(get("/successful-login"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/cart"));
	}
}