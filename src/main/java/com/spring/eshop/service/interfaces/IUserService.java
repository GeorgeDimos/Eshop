package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
	void delete(User user);
}
