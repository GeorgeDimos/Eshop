package com.spring.eshop.service.interfaces;

import com.spring.eshop.service.implementations.user.actions.UserConfirmTemplate;
import com.spring.eshop.service.implementations.user.requests.UserRequestTemplate;

public interface IUserRequests {

	void request(UserRequestTemplate template);

	void action(UserConfirmTemplate template);
}
