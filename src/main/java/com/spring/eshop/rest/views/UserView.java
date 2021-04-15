package com.spring.eshop.rest.views;

import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import org.springframework.hateoas.server.core.Relation;

import java.util.HashSet;
import java.util.Set;

@Relation(collectionRelation = "users")
public class UserView {
	public int id;
	public String username;
	public Boolean enabled;
	public String firstName;
	public String lastName;
	public String email;
	public Set<String> authority;

	public UserView(User user) {
		id = user.getId();
		username = user.getUsername();
		enabled = user.getEnabled();
		firstName = user.getUserInfo().getFirstName();
		lastName = user.getUserInfo().getLastName();
		email = user.getUserInfo().getEmail();
		authority = new HashSet<>();
		for (AuthGroup authGroup : user.getAuthGroup()) {
			authority.add(authGroup.getAuthority());
		}
	}
}
