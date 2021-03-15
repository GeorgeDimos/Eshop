package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.interfaces.IOrderRegistration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class OrderRegistration implements IOrderRegistration {
	private final OrderDAO orderDAO;
	private final ProductDAO productDAO;
	private final ApplicationEventPublisher publisher;

	public OrderRegistration(OrderDAO orderDAO, ProductDAO productDAO, ApplicationEventPublisher publisher) {
		this.orderDAO = orderDAO;
		this.productDAO = productDAO;
		this.publisher = publisher;
	}

	@Transactional
	public int execute(User user, Map<Product, Integer> shoppingCart) {
		Order order = new Order();
		Set<OrderItem> orderItems = new HashSet<>();

		shoppingCart.forEach((product, quantity) -> {

			if (productDAO.order(product.getId(), quantity) == 0) {
				throw new NotEnoughStockException(product.getName());
			}

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(quantity);

			orderItems.add(orderItem);
		});

		order.setItems(orderItems);
		order.setUser(user);
		orderDAO.save(order);

		publisher.publishEvent(new OrderReceivedEvent(order, user.getUserInfo().getEmail()));
		return order.getId();
	}
}
