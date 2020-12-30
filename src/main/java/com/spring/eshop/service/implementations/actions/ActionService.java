package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.service.implementations.actions.confirm.ConfirmAction;
import com.spring.eshop.service.implementations.actions.request.RequestEmail;
import com.spring.eshop.service.implementations.actions.request.RequestRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ActionService {

    private final UserDAO userDAO;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenDAO confirmationTokenDAO;
    private final ProductDAO productDAO;

    @Autowired
    public ActionService(UserDAO userDAO, ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder, ConfirmationTokenDAO confirmationTokenDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenDAO = confirmationTokenDAO;
        this.productDAO = productDAO;
    }

    @Transactional
    public void register(RequestRegistration request) {
        publisher.publishEvent(request.execute(userDAO, passwordEncoder, productDAO));
    }

    public void request(RequestEmail request) {
        publisher.publishEvent(request.execute(userDAO));
    }

    @Transactional
    public void confirm(ConfirmAction action) {
        action.execute(userDAO, confirmationTokenDAO, passwordEncoder);
    }
}