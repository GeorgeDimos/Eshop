package com.spring.eshop.service;

import java.util.Map;

import com.spring.eshop.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends BasicServices<Product, Integer> {
	public void buyItems(Map<Product, Integer> list);

	public Page<Product> getProductsByCategory(Integer id, Pageable pageable);
}
