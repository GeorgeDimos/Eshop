package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
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

	private final CategoryDAO categoryDAO;

	@Autowired
	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public Category getCategory(int id) {
		return categoryDAO.findById(id).orElseThrow();
	}

	@Override
	public Page<Category> getCategories(Pageable pageable) {
		return categoryDAO.findAll(pageable);
	}

	private static final Set<String> invalidSortingFields = Set.of("products");

	@Bean
	public FilterRegistrationBean<SortingFilter> categorySortingFilter() {

		FilterRegistrationBean<SortingFilter> sortFilter = new FilterRegistrationBean<>();
		sortFilter.setFilter(new SortingFilter(invalidSortingFields));
		sortFilter.addUrlPatterns("/categories/*");
		sortFilter.setName("categorySortingFilter");
		return sortFilter;
	}
}
