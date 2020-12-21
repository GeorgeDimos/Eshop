package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class OrderService implements IOrderService {

	private final OrderDAO orderDAO;
	private final ApplicationEventPublisher publisher;
	private final ProductDAO productDAO;

	@Autowired
	public OrderService(OrderDAO orderDAO, ApplicationEventPublisher publisher, ProductDAO productDAO) {
		this.orderDAO = orderDAO;
		this.publisher = publisher;
		this.productDAO = productDAO;
	}

	@Override
	@Transactional
	public void createOrder(User user, Map<Product, Integer> shoppingList) {

		Order order = new Order();
		order.setUser(user);

		Set<OrderItem> orderItems = new HashSet<>();

		shoppingList.forEach((product, quantity) -> {

			if (productDAO.order(product.getId(), quantity) == 0) {
				throw new NotEnoughStockException(product.getName());
			}

			orderItems.add(new OrderItem(order, product, quantity));
		});

		order.setItems(orderItems);
		orderDAO.save(order);

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
