package com.spring.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface BasicServices<T, ID> {

	public Page<T> getItems(Pageable pageable);

	public T getItem(ID id);

	public Page<T> getItemsByName(String name, Pageable pageable);

	abstract Set<String> getClassFields();
}
