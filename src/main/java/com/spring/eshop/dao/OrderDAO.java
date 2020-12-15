package com.spring.eshop.dao;

import com.spring.eshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends CrudRepository<Order, Integer> {
	Page<Order> getOrdersByUserId(int id, Pageable pageable);
}
