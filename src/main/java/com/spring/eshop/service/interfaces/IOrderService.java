package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.Product;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.NoSuchElementException;

public interface IOrderService {
	@Transactional
	void createOrder(Map<Product, Integer> list) throws NoSuchElementException;

	Order getOrder(int userId, int orderId) throws NoSuchElementException;
}
