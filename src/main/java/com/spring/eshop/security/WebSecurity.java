package com.spring.eshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

	public boolean checkUserIdOrRole(Authentication authentication, int id) {
		UserPrinciple user = (UserPrinciple) authentication.getPrincipal();

		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}

		return user.getUserId() == id;
	}
}