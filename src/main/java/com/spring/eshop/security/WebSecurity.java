package com.spring.eshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

	public boolean checkUserIdOrRole(Authentication authentication, int id) {

		UserPrinciple user;
		try {
			user = (UserPrinciple) authentication.getPrincipal();
		} catch (ClassCastException ex) {
			return false;
		}

		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}

		return user.getUserId() == id;
	}
}