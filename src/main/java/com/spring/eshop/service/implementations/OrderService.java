package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import com.spring.eshop.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderService implements IOrderService {

	private final OrderDAO orderDAO;

	@Autowired
	public OrderService(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public Page<Order> getOrdersByUser(User user, Pageable pageable) {

		return orderDAO.getOrdersByUser(user, pageable);
	}

	@Override
	public Order getOrder(User user, int orderId) throws NoSuchElementException {

		return orderDAO.getOrderByIdAndUser(orderId, user).orElseThrow();
	}
}
