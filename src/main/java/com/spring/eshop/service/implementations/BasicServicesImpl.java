package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ItemDAO;
import com.spring.eshop.service.BasicServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;

public abstract class BasicServicesImpl<T, ID> implements BasicServices<T, ID> {

	@Autowired
	private ItemDAO<T, ID> itemDAO;

	@Transactional
	@Override
	public T getItem(ID id) {
		Optional<T> item = itemDAO.findById(id);
		return item.orElse(null);
	}

	@Override
	public Page<T> getItems(Pageable pageable) {
		return itemDAO.findAll(pageable);
	}

	@Override
	public Page<T> getItemsByName(String name, Pageable pageable) {
		return itemDAO.findByNameContaining(name, pageable);
	}
}
