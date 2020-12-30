package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.exceptions.NotEnoughStockException;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderRegistration extends RequestRegistration {

    private final Order order;
    private final Map<Product, Integer> shoppingList;

    public OrderRegistration(Order order, User user, Map<Product, Integer> shoppingList) {
        super(user);
        this.order = order;
        this.shoppingList = shoppingList;
    }

    @Override
    protected void checkAndCreate(UserDAO userDAO, PasswordEncoder encoder, ProductDAO productDAO) {
        Set<OrderItem> orderItems = new HashSet<>();

        shoppingList.forEach((product, quantity) -> {

            if (productDAO.order(product.getId(), quantity) == 0) {
                throw new NotEnoughStockException(product.getName());
            }

            orderItems.add(new OrderItem(order, product, quantity));
        });


        order.setItems(orderItems);
        user.getOrders().add(order);
        order.setUser(user);
    }

    @Override
    protected ApplicationEvent response() {

        return new OrderReceivedEvent(order, user.getUserInfo().getEmail());
    }
}
