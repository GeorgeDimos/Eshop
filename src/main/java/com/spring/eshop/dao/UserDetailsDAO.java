package com.spring.eshop.dao;

import com.spring.eshop.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsDAO extends CrudRepository<UserDetails, Integer> {
	Optional<UserDetails> findByEmail(String email);
}
