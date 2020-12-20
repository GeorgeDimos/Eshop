package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Map;

public interface IOrderService {
	@Transactional
	void createOrder(User user, Map<Product, Integer> list);

	Order getOrder(User user, int orderId);

	Page<Order> getOrdersByUser(User user, Pageable pageable);
}
