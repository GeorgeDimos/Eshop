package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IProductService extends IBasicServices<Product, Integer> {
	void buyItems(Map<Product, Integer> list);

	Page<Product> getProductsByCategory(Category category, Pageable pageable);
}
