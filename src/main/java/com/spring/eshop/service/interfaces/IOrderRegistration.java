package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.Product;

import java.util.Map;

public interface IOrderRegistration {
	Order execute(Map<Product, Integer> shoppingCart);
}
