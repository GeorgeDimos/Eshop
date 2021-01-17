package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.service.implementations.actions.confirm.ConfirmAction;
import com.spring.eshop.service.implementations.actions.request.RequestEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ActionService {

    private final UserDAO userDAO;
    private final ApplicationEventPublisher publisher;
    private final ConfirmationTokenDAO confirmationTokenDAO;

    @Autowired
    public ActionService(UserDAO userDAO, ApplicationEventPublisher publisher, ConfirmationTokenDAO confirmationTokenDAO) {
        this.userDAO = userDAO;
        this.publisher = publisher;
        this.confirmationTokenDAO = confirmationTokenDAO;
    }

    public void request(RequestEmail request) {
        publisher.publishEvent(request.execute(userDAO));
    }

    @Transactional
    public void confirm(ConfirmAction action) {
        action.execute(userDAO, confirmationTokenDAO);
    }
}