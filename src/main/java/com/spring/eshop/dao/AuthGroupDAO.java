package com.spring.eshop.dao;

import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupDAO extends JpaRepository<AuthGroup, Integer> {
	List<AuthGroup> findByUser(User user);
}
