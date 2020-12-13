package com.spring.eshop.dao;

import com.spring.eshop.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface ConfirmationTokenDAO extends CrudRepository<ConfirmationToken, Integer> {
	Optional<ConfirmationToken> findByToken(String token);

	void deleteAllByCreateDateIsLessThan(Date expireDate);
}
