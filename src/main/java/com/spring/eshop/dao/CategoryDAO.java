package com.spring.eshop.dao;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {

	Page<Category> findDistinctBy(Pageable pageable);

	@Query(value = "SELECT DISTINCT p FROM Product p WHERE p.category.id = ?1")
	Page<Product> getProductsOfCategory(int id, Pageable pageable);
}
