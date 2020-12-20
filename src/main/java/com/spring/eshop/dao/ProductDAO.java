package com.spring.eshop.dao;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

	Page<Product> findByNameContaining(String name, Pageable pageable);

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByCategory(Category category, Pageable pageable);

	Optional<Product> findByIdAndStockGreaterThanEqual(int id, int stock);
}
