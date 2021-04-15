package com.spring.eshop.controller;

import com.spring.eshop.entity.*;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

	@MockBean
	IUserService userService;
	@MockBean
	IOrderService orderService;

	@Autowired
	MockMvc mockMvc;

	User user;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo(1, "first name", "last name", "mail@somewhere", user);
		AuthGroup e1 = new AuthGroup();
		e1.setAuthority("user");
		user = new User(1, "Username", "password", true, Collections.emptyList(), userInfo, Set.of(e1));
	}

	@Test
	void profileWithoutUser() throws Exception {

		mockMvc.perform(get("/user")
		)
						.andExpect(status().is4xxClientError());
	}

	@Test
	void profile() throws Exception {

		mockMvc.perform(get("/user")
						.with(user(user))
		)
						.andExpect(status().isOk())
						.andExpect(view().name("profile"))
						.andExpect(model().attributeExists("user"));
	}

	@Test
	void getNoOrdersList() throws Exception {
		given(orderService.getOrdersByUser(any(User.class), any(Pageable.class)))
						.willReturn(new PageImpl(Collections.emptyList()));

		mockMvc.perform(get("/user/orders")
						.with(user(user))
		)
						.andExpect(status().isOk())
						.andExpect(view().name("orders"))
						.andExpect(model().attributeExists("user"))
						.andExpect(model().attributeExists("orders"));
	}

	@Test
	void getOrdersList() throws Exception {
		Order order1 = new Order(1, user, Set.of(mock(OrderItem.class)));
		Order order2 = new Order(2, user, Set.of(mock(OrderItem.class)));
		Page<Order> page = new PageImpl(List.of(order1, order2));

		given(orderService.getOrdersByUser(any(User.class), any(Pageable.class)))
						.willReturn(page);

		mockMvc.perform(get("/user/orders")
						.with(user(user))
		)
						.andExpect(status().isOk())
						.andExpect(view().name("orders"))
						.andExpect(model().attributeExists("user"))
						.andExpect(model().attributeExists("orders"));
	}

	@Test
	void getOrderDetails() throws Exception {
		Order order = new Order(1, user, Set.of(
						new OrderItem(1, null, mock(Product.class), 2),
						new OrderItem(2, null, mock(Product.class), 3)
		));

		given(orderService.getOrder(any(User.class), gt(0)))
						.willReturn(order);
		mockMvc.perform(get("/user/orders/{oid}", order.getId())
						.with(user(user))
		)
						.andExpect(status().isOk())
						.andExpect(view().name("order"))
						.andExpect(model().attributeExists("order"));
	}

	@Test
	void deleteAccount() throws Exception {
		mockMvc.perform(get("/user/deleteAccount")
						.with(user(user))
		)
				.andExpect(status().isOk())
				.andExpect(view().name("deleteAccount"));
	}

	@Test
	void confirmDeleteAccount() throws Exception {

		mockMvc.perform(post("/user/deleteAccount")
						.with(user(user))
						.with(csrf())
						.param("confirm", "ok")
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/login?logout"));
		verify(userService).delete(user);
	}

	@Test
	void cancelDeleteAccount() throws Exception {
		mockMvc.perform(post("/user/deleteAccount")
						.with(user(user))
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/user"));
		verify(userService, never()).delete(any(User.class));
	}
}