package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.OrderItemDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class OrderService implements IOrderService {

	private final OrderDAO orderDAO;
	private final OrderItemDAO orderItemDAO;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public OrderService(OrderDAO orderDAO, OrderItemDAO orderItemDAO, ApplicationEventPublisher publisher) {
		this.orderDAO = orderDAO;
		this.orderItemDAO = orderItemDAO;
		this.publisher = publisher;
	}

	@Override
	@Transactional
	public void createOrder(User user, Map<Product, Integer> list) {

		Order order = new Order();
		order.setUser(user);
		orderDAO.save(order);

		list.forEach((product, quantity) -> {
			OrderItem item = new OrderItem(order, product, quantity);
			orderItemDAO.save(item);
		});

		publisher.publishEvent(new OrderReceivedEvent(order, user.getUserInfo().getEmail()));
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
