package com.spring.eshop.dao;

import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends ItemDAO<Product, Integer> {
	Page<Product> findByCategoryId(Integer id, Pageable pageable);
}
