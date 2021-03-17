package com.spring.eshop.rest.views;

import com.spring.eshop.entity.User;

public class UserView {
	public int id;
	public String username;
	public Boolean enabled;
	public String firstName;
	public String lastName;
	public String email;
	public String authority;

	public UserView(User user) {
		id = user.getId();
		username = user.getUsername();
		enabled = user.getEnabled();
		firstName = user.getUserInfo().getFirstName();
		lastName = user.getUserInfo().getLastName();
		email = user.getUserInfo().getEmail();
		authority = user.getAuthGroup().getAuthority();
	}
}
