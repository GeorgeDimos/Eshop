package com.spring.eshop.service;

import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductService extends BasicServices<Product, Integer> {
	void buyItems(Map<Product, Integer> list);

	Page<Product> getProductsByCategory(Integer id, Pageable pageable);
}