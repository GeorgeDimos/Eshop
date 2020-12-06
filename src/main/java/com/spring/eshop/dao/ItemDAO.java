package com.spring.eshop.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ItemDAO<T, ID> extends JpaRepository<T, ID> {
	public Page<T> findByNameContaining(String name, Pageable pageable);

	public Page<T> findAll(Pageable pageable);
}
