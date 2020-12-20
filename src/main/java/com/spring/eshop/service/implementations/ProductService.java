package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductService implements IProductService {

	private static final Set<String> invalidSortingFields = Set.of("images", "category", "description");

	private final ProductDAO productDAO;

	@Autowired
	public ProductService(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public Product getProduct(int id) {
		return productDAO.findById(id).orElseThrow();
	}

	@Override
	public Page<Product> getProducts(Pageable pageable) {
		return productDAO.findAll(pageable);
	}

	@Override
	public Page<Product> getProductsByName(String name, Pageable pageable) {
		return productDAO.findByNameContaining(name, pageable);
	}

	@Override
	public Page<Product> getProductsByCategory(Category category, Pageable pageable) {
		return productDAO.findByCategory(category, pageable);
	}

	@Bean
	public FilterRegistrationBean<SortingFilter> productSortingFilter() {

		FilterRegistrationBean<SortingFilter> sortFilter = new FilterRegistrationBean<>();
		sortFilter.setFilter(new SortingFilter(invalidSortingFields));
		sortFilter.addUrlPatterns("/products/*");
		sortFilter.setName("productSortingFilter");

		return sortFilter;
	}
}
