package com.spring.eshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "data")
	private byte[] data;

	public Image() {
	}

	public Image(int id, String name, byte[] data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}
}