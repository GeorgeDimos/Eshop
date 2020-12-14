package com.spring.eshop.service.implementations;

import com.spring.eshop.entity.Category;
import com.spring.eshop.service.interfaces.ICategoryService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryService extends BasicServices<Category, Integer> implements ICategoryService {

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
