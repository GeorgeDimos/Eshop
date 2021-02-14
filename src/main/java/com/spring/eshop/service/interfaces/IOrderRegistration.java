package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;

import java.util.Map;

public interface IOrderRegistration {
	int execute(User user, Map<Product, Integer> shoppingCart);
}
