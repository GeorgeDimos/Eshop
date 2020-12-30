package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class RequestRegistration {

    protected final User user;

    protected RequestRegistration(User user) {
        this.user = user;
    }


    public final ApplicationEvent execute(UserDAO userDAO, PasswordEncoder encoder, ProductDAO productDAO) {
        checkAndCreate(userDAO, encoder, productDAO);
        userDAO.save(user);
        return response();
    }

    protected abstract void checkAndCreate(UserDAO userDAO, PasswordEncoder encoder, ProductDAO productDAO);

    protected abstract ApplicationEvent response();
}
