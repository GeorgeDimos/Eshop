package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.actions.OrderRegistration;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

	@MockBean
	ShoppingCart shoppingCart;
	@MockBean
	IUserService userService;
	@MockBean
	OrderRegistration orderRegistration;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	void checkoutWithoutUser() throws Exception {

		mockMvc.perform(get("/checkout"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser
	void checkoutLoggedIn() throws Exception {

		given(shoppingCart.getShoppingCart()).willReturn(mock(Map.class));

		mockMvc.perform(get("/checkout"))
				.andExpect(status().isOk())
				.andExpect(view().name("checkout"))
				.andExpect(model().attributeExists("shoppingList"));
	}

	@Test
	@WithMockUser
	void buyProductsCancel() throws Exception {
		given(shoppingCart.isEmpty()).willReturn(false);
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", 1)
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
		then(orderRegistration).shouldHaveNoMoreInteractions();
		then(shoppingCart).shouldHaveNoInteractions();
	}

	@Test
	@WithMockUser
	void buyProductsEmptyCart() throws Exception {
		given(shoppingCart.isEmpty()).willReturn(true);
		then(shoppingCart).shouldHaveNoMoreInteractions();
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", 1)
				.param("confirm", "OK")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
		then(orderRegistration).shouldHaveNoInteractions();
	}

	@Test
	@WithMockUser
	void buyProducts() throws Exception {
		given(shoppingCart.isEmpty()).willReturn(false);
		given(userService.getUserById(gt(0))).willReturn(mock(User.class));
		given(shoppingCart.getShoppingCart()).willReturn(mock(Map.class));
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", 1)
				.param("confirm", "OK")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/user/orders"));

		then(orderRegistration).should().execute(any(User.class), any(Map.class));
		then(shoppingCart).should().clear();
	}

	@Test
	@WithMockUser
	void notEnoughStockInOrder() throws Exception {
		given(userService.getUserById(gt(0))).willReturn(mock(User.class));
		given(shoppingCart.getShoppingCart()).willReturn(mock(Map.class));
		doThrow(mock(NotEnoughStockException.class))
				.when(orderRegistration).execute(any(User.class), any(Map.class));
		given(shoppingCart.isEmpty()).willReturn(false);
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", 1)
				.param("confirm", "OK")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"))
				.andExpect(flash().attributeExists("notEnoughStockError"));
		verify(shoppingCart, never()).clear();
	}
}