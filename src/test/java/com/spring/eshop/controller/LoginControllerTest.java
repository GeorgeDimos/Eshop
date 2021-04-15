package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	@Test
	void loginView() throws Exception {
		mockMvc.perform(get("/login"))
						.andExpect(status().isOk())
						.andExpect(view().name("login"));
	}

	@Test
	@WithMockUser
	void loginViewWhileAlreadyLoggedIn() throws Exception {
		mockMvc.perform(get("/login"))
						.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser
	void redirectAfterLoginEmptyShoppingCart() throws Exception {
		given(shoppingCart.getItemsList()).willReturn(Collections.emptyMap());

		mockMvc.perform(get("/successful-login"))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/products"));
	}

	@Test
	@WithMockUser
	void redirectAfterLoginShoppingCartHasItems() throws Exception {
		given(shoppingCart.getItemsList()).willReturn(mock(Map.class));

		mockMvc.perform(get("/successful-login"))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/cart"));
	}

	@Test
	@WithMockUser
	void logout() throws Exception {
		mockMvc.perform(get("/logout"))
						.andExpect(status().isOk())
						.andExpect(view().name("logout"));
	}
}