package com.spring.eshop.service.interfaces;

import com.spring.eshop.service.implementations.actions.confirm.ConfirmTemplate;
import com.spring.eshop.service.implementations.actions.request.RequestTemplate;

public interface IUserRequests {

	void request(RequestTemplate template);

	void confirm(ConfirmTemplate template);
}
