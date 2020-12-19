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
	private Boolean enabled = false;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Order> orders;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserInfo userInfo;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AuthGroup authGroup;
}
