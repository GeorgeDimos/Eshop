package com.spring.eshop.service.implementations;

import java.util.Set;

import com.spring.eshop.entity.Category;
import com.spring.eshop.service.CategoryService;
import com.spring.eshop.service.SortingFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BasicServicesImpl<Category, Integer> implements CategoryService {

	private static final Set<String> invalidSortingFields = Set.of("products");

	@Override
	public final Set<String> getClassFields() {
		return invalidSortingFields;
	}

	@Bean
	public FilterRegistrationBean<SortingFilter> categorySortingFilter(){

		FilterRegistrationBean<SortingFilter> sortFilter = new FilterRegistrationBean<>();
		sortFilter.setFilter(new SortingFilter(invalidSortingFields));
		sortFilter.addUrlPatterns("/categories/*");
		sortFilter.setName("categorySortingFilter");
		return sortFilter;    
	}

}
