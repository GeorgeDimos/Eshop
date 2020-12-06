package com.spring.eshop.securingweb;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {
	public boolean checkUserId(Authentication authentication, int id) {
		UserPrinciple user = (UserPrinciple) authentication.getPrincipal();
		if (user.getUserId() == id) {
			return true;
		}
		return false;
	}
}