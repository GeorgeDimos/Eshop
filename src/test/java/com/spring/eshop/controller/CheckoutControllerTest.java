package com.spring.eshop.controller;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.OrderRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

	@SpyBean
	ShoppingCart shoppingCart;

	@MockBean
	OrderRegistration orderRegistration;

	@Autowired
	MockMvc mockMvc;

	@Test
	void checkoutWithoutUser() throws Exception {
		mockMvc.perform(get("/checkout"))
						.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser
	void checkoutLoggedIn() throws Exception {
		mockMvc.perform(get("/checkout"))
				.andExpect(status().isOk())
				.andExpect(view().name("checkout"))
				.andExpect(model().attributeExists("shoppingCart"));
	}

	@Test
	@WithMockUser
	void buyProductsCancel() throws Exception {
		given(shoppingCart.isEmpty()).willReturn(false);
		mockMvc.perform(post("/checkout")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/products"));
		then(orderRegistration).shouldHaveNoInteractions();
		then(shoppingCart).shouldHaveNoInteractions();
	}

	@Test
	@WithMockUser
	void buyProductsEmptyCart() throws Exception {
		given(shoppingCart.isEmpty()).willReturn(true);
		then(shoppingCart).shouldHaveNoMoreInteractions();
		mockMvc.perform(post("/checkout")
				.param("confirm", "OK")
				.with(csrf())
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
		then(orderRegistration).shouldHaveNoInteractions();
	}

	@Test
	@WithMockUser
	void buyProducts() throws Exception {
		shoppingCart.add(mock(Product.class), 2);
		shoppingCart.add(mock(Product.class), 3);
		given(orderRegistration.execute(any(Map.class))).willReturn(mock(Order.class));

		mockMvc.perform(post("/checkout")
						.param("confirm", "OK")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/checkout/order"));

		verify(orderRegistration).execute(any(Map.class));
		assertTrue(shoppingCart.getItemsList().isEmpty());
	}

	@Test
	@WithMockUser
	void notEnoughStockInOrder() throws Exception {

		doThrow(NotEnoughStockException.class)
						.when(orderRegistration).execute(any(Map.class));

		shoppingCart.add(mock(Product.class), 2);
		mockMvc.perform(post("/checkout")
						.param("confirm", "OK")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/cart"))
						.andExpect(flash().attributeExists("notEnoughStockError"));
		verify(shoppingCart, never()).clear();
	}

	@Test
	@WithMockUser
	void getOrderViewAfterCheckout() throws Exception {
		mockMvc.perform(get("/checkout/order")
						.flashAttr("order", mock(Order.class))
		)
						.andExpect(status().isOk())
						.andExpect(view().name("order"));
	}

	@Test
	@WithMockUser
	void getOrderViewWithoutCheckout() throws Exception {
		mockMvc.perform(get("/checkout/order"))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/home"));
	}
}