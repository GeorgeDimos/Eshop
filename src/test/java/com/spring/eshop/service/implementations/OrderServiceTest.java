package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	OrderDAO dao;

	@InjectMocks
	OrderService service;

	@Test
	void getOrdersByUser() {
		User user = mock(User.class);
		Pageable pageable = mock(Pageable.class);
		Page<Order> orders = new PageImpl<>(List.of(
						mock(Order.class),
						mock(Order.class)
		));
		when(dao.getOrdersByUser(any(User.class), any(Pageable.class))).thenReturn(orders);

		Page<Order> result = service.getOrdersByUser(user, pageable);

		verify(dao).getOrdersByUser(user, pageable);
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(2);
	}

	@Test
	void getOrder() {
		Order order = mock(Order.class);
		when(dao.getOrderByIdAndUser(gt(0), any(User.class))).thenReturn(Optional.of(order));
		User user = mock(User.class);
		Order result = service.getOrder(user, 1);
		verify(dao).getOrderByIdAndUser(1, user);
		assertThat(result).isNotNull();
	}
}