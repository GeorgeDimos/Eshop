package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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
	void redirectAfterLoginEmptyShoppingCart() throws Exception {

		given(userService.getCurrentUser()).willReturn(mock(User.class));
		given(shoppingCart.getShoppingCart()).willReturn(Collections.EMPTY_MAP);

		mockMvc.perform(get("/successful-login"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/products"));
	}

	@Test
	void redirectAfterLoginShoppingCartHasItems() throws Exception {

		given(userService.getCurrentUser()).willReturn(mock(User.class));
		given(shoppingCart.getShoppingCart()).willReturn(mock(Map.class));

		mockMvc.perform(get("/successful-login"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/cart"));
	}
}