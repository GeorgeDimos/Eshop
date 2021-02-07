package com.spring.eshop.dao;

import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

	Page<Product> findDistinctByNameContaining(String name, Pageable pageable);

	Page<Product> findDistinctBy(Pageable pageable);

	@Modifying
	@Query(value = "update product p set p.stock = (p.stock - ?2) where p.id = ?1 AND p.stock >= ?2 AND ?2 > 0", nativeQuery = true)
	int order(int id, int quantity);
}
