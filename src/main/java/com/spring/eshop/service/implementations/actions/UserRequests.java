package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.implementations.actions.confirm.ConfirmTemplate;
import com.spring.eshop.service.implementations.actions.request.RequestTemplate;
import com.spring.eshop.service.interfaces.IUserRequests;
import com.spring.eshop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserRequests implements IUserRequests {

	private final ConfirmationTokenDAO confirmationTokenDAO;
	private final IUserService userService;
	private final AuthGroupService authGroupService;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public UserRequests(ConfirmationTokenDAO confirmationTokenDAO, IUserService userService, AuthGroupService authGroupService, ApplicationEventPublisher publisher) {
		this.confirmationTokenDAO = confirmationTokenDAO;
		this.userService = userService;
		this.authGroupService = authGroupService;
		this.publisher = publisher;
	}

	public void request(RequestTemplate request) {
		request.execute(publisher, userService, authGroupService);
	}

	@Transactional
	public void confirm(ConfirmTemplate action) {
		action.execute(confirmationTokenDAO, userService);
	}
}
