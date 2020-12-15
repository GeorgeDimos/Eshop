package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.OrderItemDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class OrderService implements IOrderService {

	private final IUserService userService;
	private final OrderDAO orderDAO;
	private final OrderItemDAO orderItemDAO;

	@Autowired
	public OrderService(IUserService userService, OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
		this.userService = userService;
		this.orderDAO = orderDAO;
		this.orderItemDAO = orderItemDAO;
	}

	@Override
	@Transactional
	public void createOrder(Map<Product, Integer> list) throws NoSuchElementException {

		UserPrinciple currentUserPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		User user = userService.getUserById(currentUserPrinciple.getUserId());

		Order order = new Order();
		order.setUser(user);
		orderDAO.save(order);

		list.forEach((product, quantity) -> {
			OrderItem item = new OrderItem(order, product, quantity);
			orderItemDAO.save(item);
		});
	}

	@Override
	public Page<Order> getOrdersByUserId(int id, Pageable pageable) {
		return orderDAO.getOrdersByUserId(id, pageable);
	}

	@Override
	public Order getOrder(int userId, int orderId) throws NoSuchElementException {
		User user = userService.getUserById(userId);
		if (orderId > user.getOrders().size() - 1 || orderId < 0) {
			throw new NoSuchElementException();
		}
		return user.getOrders().get(orderId);
	}
}
