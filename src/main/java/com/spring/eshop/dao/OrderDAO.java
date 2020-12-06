package com.spring.eshop.dao;

import com.spring.eshop.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends CrudRepository<Order, Integer> {
}
