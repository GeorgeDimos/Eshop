package com.spring.eshop.dao;

import com.spring.eshop.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDAO extends CrudRepository<OrderItem, Integer> {
}
