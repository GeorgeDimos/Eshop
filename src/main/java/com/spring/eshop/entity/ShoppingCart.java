package com.spring.eshop.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope
public class ShoppingCart {

	private final Map<Product, Integer> itemsList;

	public ShoppingCart() {
		this.itemsList = new HashMap<>();
	}

	public ShoppingCart(Map<Product, Integer> itemsList) {
		this.itemsList = itemsList;
	}

	public Map<Product, Integer> getItemsList() {
		return this.itemsList;
	}

	public double getSum() {
		double sum = 0;
		for (Product p : itemsList.keySet()) {
			sum += p.getPrice() * itemsList.get(p);
		}
		return sum;
	}

	public void add(Product product, int quantity) {
		itemsList.merge(product, quantity,
				(oldQuantity, newQuantity) -> newQuantity + oldQuantity > product.getStock() ? oldQuantity
						: newQuantity + oldQuantity);
	}

	public void remove(Product product) {
		itemsList.remove(product);
	}

	@Override
	public String toString() {
		return "{" + " itemsList='" + itemsList + "'" + "}";
	}

	public void clear() {
		itemsList.clear();
	}

	public boolean isEmpty() {
		return itemsList.isEmpty();
	}
}
