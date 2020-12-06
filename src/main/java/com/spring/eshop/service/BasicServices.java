package com.spring.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasicServices<T, ID> {

	Page<T> getItems(Pageable pageable);

	T getItem(ID id);

	Page<T> getItemsByName(String name, Pageable pageable);
}
