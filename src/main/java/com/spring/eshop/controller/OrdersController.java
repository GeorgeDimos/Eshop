package com.spring.eshop.controller;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profiles/{pid}/orders")
public class OrdersController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    public String getOrdersList(@PathVariable int pid,
                                Model model){
        model.addAttribute("user", userDAO.findById(pid).get());
        return "orders";
    }

    @GetMapping("/{oid}")
    public String getOrderDetails(@PathVariable int oid, Model model){
        model.addAttribute("order", orderDAO.findById(oid).get());
        return "order";
    }
}
