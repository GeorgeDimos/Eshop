package com.spring.eshop.controller;

import com.spring.eshop.entity.*;
import com.spring.eshop.security.UserPrinciple;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsersController.class)
class UsersControllerTest {

	@MockBean
	IUserService userService;

	@MockBean
	IOrderService orderService;

	@MockBean
	UserPrinciple userPrinciple;

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
		UserInfo userInfo = new UserInfo(1, "first name", "last name", "mail@somewhere", null);
		user = new User(1, "Username", "password", true, null, userInfo, null);
	}

	@Test
	void profile() throws Exception {

		mockMvc.perform(get("/user")
				.with(user(new UserPrinciple(user, Collections.EMPTY_LIST)))
		)
				.andExpect(status().isOk())
				.andExpect(view().name("profile"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	void getOrdersList() throws Exception {
		Order order = new Order(1, user, Set.of(mock(OrderItem.class)));
		Page<Order> page = new PageImpl(List.of(order));

		given(orderService.getOrdersByUser(any(User.class), any(Pageable.class)))
				.willReturn(page);

		mockMvc.perform(get("/user/orders")
				.with(user(new UserPrinciple(user, Collections.EMPTY_LIST)))
		)
				.andExpect(status().isOk())
				.andExpect(view().name("orders"))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("orders"));
	}

	@Test
	void getOrderDetails() throws Exception {
		Order order = new Order(1, user, null);
		order.setItems(Set.of(
				new OrderItem(1, order, mock(Product.class), 2)
		));

		given(orderService.getOrder(any(), gt(0)))
				.willReturn(order);
		mockMvc.perform(get("/user/orders/{oid}", order.getId())
				.with(user(mock(UserPrinciple.class)))
		)
				.andExpect(status().isOk())
				.andExpect(view().name("order"))
				.andExpect(model().attributeExists("order"));
	}

}