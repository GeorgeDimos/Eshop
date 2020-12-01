package com.spring.eshop.service.implementations;

import javax.transaction.Transactional;

import com.spring.eshop.dao.ItemDAO;
import com.spring.eshop.service.BasicServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BasicServicesImpl<T, ID> implements BasicServices<T, ID> {

	@Autowired
	private ItemDAO<T, ID> itemDAO;

	@Transactional
	@Override
	public T getItem(ID id) {
		return itemDAO.findById(id).get();
	}

	@Override
	public Page<T> getItems(Pageable pageable) {
		return itemDAO.findAll(filterPageable(pageable));
	}

	@Override
	public Page<T> getItemsByName(String name, Pageable pageable) {
		return itemDAO.findByNameContaining(name, pageable);
	}

	/*************************************************** Helper Functions *********************************************/

	public Pageable filterPageable(Pageable pageable){

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
			Sort.by(pageable.getSort()
				.filter( order -> !getClassFields().contains(order.getProperty()))
				.toList()
			)
		);
	}

}
