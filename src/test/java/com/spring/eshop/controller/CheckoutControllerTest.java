package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.actions.ActionService;
import com.spring.eshop.service.implementations.actions.request.OrderRegistration;
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

import java.util.List;
import java.util.Map;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
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
	ActionService actionService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	Map<Product, Integer> cart;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		Category c1 = new Category(1, "test_category", null);
		Product product = new Product(1,
				"test_product_1",
				"test_description_1",
				10, 10, List.of(), c1);
		cart = Map.of(product, product.getId());
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
		given(shoppingCart.getShoppingCart()).willReturn(cart);

		mockMvc.perform(get("/checkout"))
				.andExpect(status().isOk())
				.andExpect(view().name("checkout"))
				.andExpect(model().attributeExists("shoppingList"));
	}

	@Test
	@WithMockUser
	void buyProductsCancel() throws Exception {
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", 1)
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
	}

	@Test
	@WithMockUser
	void buyProducts() throws Exception {
		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", gt(0))
				.param("confirm", "OK")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));

		then(actionService).should().register(any(OrderRegistration.class));
		then(shoppingCart).should().clear();
	}

	@Test
	@WithMockUser
	void notEnoughStockInOrder() throws Exception {
		doThrow(new NotEnoughStockException("TestProduct"))
				.when(actionService).register(any(OrderRegistration.class));

		mockMvc.perform(post("/checkout")
				.sessionAttr("user_id", gt(0))
				.param("confirm", "OK")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"))
				.andExpect(flash().attributeExists("notEnoughStockError"));
	}
}