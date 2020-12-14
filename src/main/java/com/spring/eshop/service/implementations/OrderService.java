package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.OrderItemDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class OrderService {

	private final UserService userService;
	private final OrderDAO orderDAO;
	private final OrderItemDAO orderItemDAO;

	@Autowired
	public OrderService(UserService userService, OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
		this.userService = userService;
		this.orderDAO = orderDAO;
		this.orderItemDAO = orderItemDAO;
	}

	@Transactional
	public void createNewOrder(Map<Product, Integer> list) throws NoSuchElementException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple currentUserPrinciple = (UserPrinciple) authentication.getPrincipal();
		User user = userService.getUserById(currentUserPrinciple.getUserId());

		Order order = new Order();
		order.setUser(user);
		orderDAO.save(order);

		list.forEach((product, quantity) -> {
			OrderItem item = new OrderItem(order, product, quantity);
			orderItemDAO.save(item);
		});
	}

	public Order getOrderByUserAndId(int userId, int orderId) throws NoSuchElementException {
		User user = userService.getUserById(userId);
		Order order = orderDAO.findById(orderId).orElseThrow(NoSuchElementException::new);
		if (order.getUser() != user) {
			throw new NoSuchElementException();
		}
		return order;
	}
}
