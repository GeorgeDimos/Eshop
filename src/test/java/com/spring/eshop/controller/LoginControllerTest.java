package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.interfaces.IUserService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
	void loginWhileAlreadyLoggedIn() throws Exception {
		mockMvc.perform(get("/login").sessionAttr("user_id", "anything"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/user"));
	}

	@Test
	void redirectAfterLoginEmptyShoppingCart() throws Exception {
		given(shoppingCart.getShoppingCart()).willReturn(Collections.EMPTY_MAP);

		mockMvc.perform(get("/successful-login").with(user(mock(UserPrinciple.class))))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
	}

	@Test
	@WithMockUser
	void redirectAfterLoginShoppingCartHasItems() throws Exception {
		given(shoppingCart.getShoppingCart()).willReturn(mock(Map.class));

		mockMvc.perform(get("/successful-login").with(user(mock(UserPrinciple.class))))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"));
	}
}