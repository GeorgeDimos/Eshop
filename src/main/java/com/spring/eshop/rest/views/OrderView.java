package com.spring.eshop.rest.views;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "orders")
public class OrderView {
	public int id;
	public int userId;
	public Set<OrderItemView> items;

	public OrderView() {
	}

	public OrderView(Order order) {
		id = order.getId();
		if (order.getUser() != null)
			userId = order.getUser().getId();
		items = order.getItems().stream().map(OrderItemView::new).collect(Collectors.toSet());
	}

	public static void updateQuantities(Order order, OrderView orderView) {
		order.getItems().forEach(orderItem -> orderView.items.stream()
						.filter(orderItemView -> orderItemView.productId == orderItem.getProduct().getId())
						.forEach(orderItemView ->
										orderItem.setQuantity(orderItemView.quantity)
						));
	}

	public static class OrderItemView {
		@NotNull
		public int productId;
		public String name;
		@Min(1)
		public int quantity;

		public OrderItemView() {
		}

		public OrderItemView(OrderItem item) {
			productId = item.getProduct().getId();
			name = item.getProduct().getName();
			quantity = item.getQuantity();
		}
	}
}