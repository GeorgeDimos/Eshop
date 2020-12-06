package com.spring.eshop.exceptions;

public class NotEnoughStockException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String productName;

	public NotEnoughStockException(String productName) {
		super("Out of stock");
		this.productName = productName;
	}

	public String getProductName() {
		return this.productName;
	}
}
