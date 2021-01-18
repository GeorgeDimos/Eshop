package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {

	private final CategoryDAO categoryDAO;

	@Autowired
	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public Page<Category> getCategories(Pageable pageable) {
		return categoryDAO.findDistinctBy(pageable);
	}

	@Override
	public Page<Product> getProductsOfCategory(int id, Pageable pageable) {
		return categoryDAO.getProductsOfCategory(id, pageable);
	}

}
