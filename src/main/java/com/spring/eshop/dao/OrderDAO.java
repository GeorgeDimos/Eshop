package com.spring.eshop.dao;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
	Page<Order> getOrdersByUser(User user, Pageable pageable);

	Optional<Order> getOrderByIdAndUser(int id, User user);
}
