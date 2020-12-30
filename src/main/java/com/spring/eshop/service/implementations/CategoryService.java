package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryService implements ICategoryService {

	private static final Set<String> invalidSortingFields = Set.of("products");
	private final CategoryDAO categoryDAO;

	@Autowired
	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public Page<Category> getCategories(Pageable pageable) {
		return categoryDAO.findAll(pageable);
	}

	@Override
	public Page<Product> getProductsOfCategory(int id, Pageable pageable) {
		return categoryDAO.getProductsOfCategory(id, pageable);
	}

	@Bean
	public FilterRegistrationBean<SortingFilter> categorySortingFilter() {

		FilterRegistrationBean<SortingFilter> sortFilter = new FilterRegistrationBean<>();
		sortFilter.setFilter(new SortingFilter(invalidSortingFields));
		sortFilter.addUrlPatterns("/categories/*");
		sortFilter.setName("categorySortingFilter");
		return sortFilter;
	}
}
