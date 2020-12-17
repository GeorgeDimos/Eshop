package com.spring.eshop.dao;

import com.spring.eshop.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
}
