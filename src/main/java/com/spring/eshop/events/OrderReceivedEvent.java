package com.spring.eshop.events;

import com.spring.eshop.entity.Order;
import org.springframework.context.ApplicationEvent;

public class OrderReceivedEvent extends ApplicationEvent {

	private final Order order;
	private final String email;

	public OrderReceivedEvent(Order order, String email) {
		super(order);
		this.order = order;
		this.email = email;
	}

	public Order getOrder() {
		return order;
	}

	public String getEmail() {
		return email;
	}
}
