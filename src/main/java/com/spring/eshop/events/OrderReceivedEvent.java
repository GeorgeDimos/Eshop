package com.spring.eshop.events;

import com.spring.eshop.entity.Order;

public class OrderReceivedEvent{

	private final Order order;
	private final String email;

	public OrderReceivedEvent(Order order, String email) {
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
