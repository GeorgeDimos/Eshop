package com.spring.eshop.dao;

import com.spring.eshop.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupDAO extends JpaRepository<AuthGroup, Integer> {
}
