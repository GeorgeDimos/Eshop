package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.exceptions.NotEnoughStockException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class OrderRegistration {
	private final UserDAO userDAO;
	private final ProductDAO productDAO;
	private final ApplicationEventPublisher publisher;

	public OrderRegistration(UserDAO userDAO, ProductDAO productDAO, ApplicationEventPublisher publisher) {
		this.userDAO = userDAO;
		this.productDAO = productDAO;
		this.publisher = publisher;
	}

	@Transactional
	public void execute(User user, Map<Product, Integer> shoppingCart) {
		Order order = new Order();
		Set<OrderItem> orderItems = new HashSet<>();

		shoppingCart.forEach((product, quantity) -> {

			if (productDAO.order(product.getId(), quantity) == 0) {
				throw new NotEnoughStockException(product.getName());
			}

			orderItems.add(new OrderItem(order, product, quantity));
		});

		order.setItems(orderItems);
		user.getOrders().add(order);
		order.setUser(user);
		userDAO.save(user);

		publisher.publishEvent(new OrderReceivedEvent(order, user.getUserInfo().getEmail()));
	}
}
