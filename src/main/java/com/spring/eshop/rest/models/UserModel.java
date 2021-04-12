package com.spring.eshop.rest.models;

import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Set;

public class UserModel {

	@NotBlank(message = "{username.blank}")
	public String username;
	@NotBlank(message = "{password.blank}")
	public String password;
	public Boolean enabled;

	@NotBlank(message = "{firstname.blank}")
	public String firstName;
	@NotBlank(message = "{lastname.blank}")
	public String lastName;
	@Pattern(regexp = "^(.+)@(.+)", message = "{email.invalid}")
	public String email;

	public String authority;

	public static void updateUser(User user, UserModel userModel) {
		user.setUsername(userModel.username);
		user.setPassword(userModel.password);
		user.setEnabled(userModel.enabled);
		user.getUserInfo().setEmail(userModel.email);
		user.getUserInfo().setFirstName(userModel.firstName);
		user.getUserInfo().setLastName(userModel.lastName);
		AuthGroup authGroup = new AuthGroup();
		authGroup.setAuthority(userModel.authority);
		user.getAuthGroup().add(authGroup);
	}

	public User translateToUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEnabled(enabled);
		user.setOrders(new ArrayList<>());

		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(firstName);
		userInfo.setLastName(lastName);
		userInfo.setEmail(email);
		userInfo.setUser(user);
		user.setUserInfo(userInfo);

		AuthGroup authGroup = new AuthGroup();
		authGroup.setAuthority(authority);
		authGroup.setUser(user);
		user.setAuthGroup(Set.of(authGroup));

		return user;
	}
}
