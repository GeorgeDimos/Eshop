package com.spring.eshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotBlank(message = "{username.blank}")
	@Column(name = "username")
	private String username;

	@NotBlank(message = "{password.blank}")
	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private List<Order> orders;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private UserDetails userDetails;
}
