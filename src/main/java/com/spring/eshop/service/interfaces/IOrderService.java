package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {

	Order getOrder(User user, int orderId);

	Page<Order> getOrdersByUser(User user, Pageable pageable);
}
