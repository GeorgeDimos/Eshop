package com.spring.eshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "token")
	private String token;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
