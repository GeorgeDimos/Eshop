package com.spring.eshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotBlank(message = "{firstname.blank}")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "{lastname.blank}")
	@Column(name = "last_name")
	private String lastName;

	@Pattern(regexp = "^(.+)@(.+)$", message = "{email.invalid}")
	@Column(name = "email")
	private String email;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
