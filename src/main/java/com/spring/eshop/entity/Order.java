package com.spring.eshop.entity;

import javax.persistence.*;
import javax.transaction.Transactional;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "customer_id")
	private int customerId;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private List<OrderItem> items;

}
