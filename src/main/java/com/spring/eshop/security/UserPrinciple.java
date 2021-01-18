package com.spring.eshop.security;

import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrinciple implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final transient User user;
	private final transient List<AuthGroup> authGroup;

	public UserPrinciple(User user, List<AuthGroup> authGroup) {
		this.user = user;
		this.authGroup = authGroup;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authGroup == null) {
			return Collections.emptySet();
		}

		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		authGroup.forEach(group -> grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthority())));

		return grantedAuthorities;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}
}
