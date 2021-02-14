package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;

public interface IUserRegistration {

	void register(User user, UserInfo userInfo);

	void confirm(String token);
}
