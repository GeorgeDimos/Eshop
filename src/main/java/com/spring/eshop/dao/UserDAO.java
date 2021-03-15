package com.spring.eshop.dao;

import com.spring.eshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndUserInfoEmail(String username, String email);

	boolean existsByUsernameOrUserInfoEmail(String username, String email);
}
