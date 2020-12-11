package com.spring.eshop.dao;

import com.spring.eshop.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsDAO extends CrudRepository<UserDetails, Integer> {
	UserDetails findByEmail(String email);
}
