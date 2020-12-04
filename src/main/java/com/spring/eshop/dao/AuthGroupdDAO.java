package com.spring.eshop.dao;

import java.util.List;

import com.spring.eshop.entity.AuthGroup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupdDAO extends JpaRepository<AuthGroup,Integer> {
	List<AuthGroup> findByUsername(String username);	
}
