package com.spring.eshop.dao;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends ItemDAO<Product, Integer> {
	Page<Product> findByCategory(Category category, Pageable pageable);

	List<Product> findAllByIdIsIn(List<Integer> ids);
}
