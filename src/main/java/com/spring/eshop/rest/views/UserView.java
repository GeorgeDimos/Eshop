package com.spring.eshop.rest.views;

import com.spring.eshop.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserView {
	public int id;
	public String username;
	public Boolean enabled;
	public List<OrderView> orders;
	public String firstName;
	public String lastName;
	public String email;
	public String authority;

	public UserView(User user) {
		id = user.getId();
		username = user.getUsername();
		enabled = user.getEnabled();
		orders = user.getOrders().stream().map(OrderView::new).collect(Collectors.toList());
		firstName = user.getUserInfo().getFirstName();
		lastName = user.getUserInfo().getLastName();
		email = user.getUserInfo().getEmail();
		authority = user.getAuthGroup().getAuthority();
	}
}
