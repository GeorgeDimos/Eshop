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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsersController.class)
class UsersControllerTest {

	@MockBean
	IUserService userService;

	@MockBean
	IOrderService orderService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	User user;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		user = new User(1, "Username", "password", true, null, null, null);
	}

	@Test
	void profile() throws Exception {
		UserInfo info = new UserInfo(1, "Name", "Surname", "email@gmail.com", user);
		user.setUserInfo(info);
		given(userService.getUserById(gt(0))).willReturn(user);

		mockMvc.perform(get("/user").sessionAttr("user_id", user.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("profile"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	void getOrdersList() throws Exception {
		Order order = new Order(1, user, null);
		OrderItem item = new OrderItem(1, order, new Product(), 2);
		order.setItems(Set.of(item));
		Page<Order> page = new PageImpl(List.of(order));
		given(userService.getUserById(gt(0))).willReturn(user);
		given(orderService.getOrdersByUser(any(User.class), any())).willReturn(page);
		mockMvc.perform(get("/user/orders").sessionAttr("user_id", user.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("orders"))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("orders"));
	}

	@Test
	void getOrderDetails() throws Exception {
		Order order = new Order(1, user, null);
		OrderItem item = new OrderItem(1, order, new Product(), 2);
		order.setItems(Set.of(item));
		given(userService.getUserById(gt(0))).willReturn(user);
		given(orderService.getOrder(any(User.class), gt(0))).willReturn(order);
		mockMvc.perform(get("/user/orders/{oid}", order.getId()).sessionAttr("user_id", user.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("order"))
				.andExpect(model().attributeExists("order"));
	}
}