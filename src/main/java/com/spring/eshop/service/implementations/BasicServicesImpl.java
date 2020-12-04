package com.spring.eshop.service.implementations;

import java.util.Optional;

import javax.transaction.Transactional;

import com.spring.eshop.dao.ItemDAO;
import com.spring.eshop.service.BasicServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BasicServicesImpl<T, ID> implements BasicServices<T, ID> {

	@Autowired
	private ItemDAO<T, ID> itemDAO;

	@Transactional
	@Override
	public T getItem(ID id) {
		Optional<T> item = itemDAO.findById(id);
		if(item.isPresent()){
			return item.get();
		}
		return null;
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
