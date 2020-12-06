package com.spring.eshop.dao;

import com.spring.eshop.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupdDAO extends JpaRepository<AuthGroup, Integer> {
	List<AuthGroup> findByUsername(String username);
}
