package com.spring.eshop.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBasicServices<T, ID> {

	Page<T> getItems(Pageable pageable);

	T getItem(ID id);

	Page<T> getItemsByName(String name, Pageable pageable);
}
