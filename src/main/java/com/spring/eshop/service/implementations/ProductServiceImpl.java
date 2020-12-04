package com.spring.eshop.service.implementations;

import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Product;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.ProductService;
import com.spring.eshop.service.SortingFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BasicServicesImpl<Product, Integer> implements ProductService {

	private static final Set<String> invalidSortingFields = Set.of("images", "category", "description");

	@Autowired
	private ProductDAO productDAO;

	@Transactional
	@Override
	public void buyItems(Map<Product, Integer> list) {
		list.forEach((product, quantity) -> {
			int currentStock = productDAO.findById(product.getId()).get().getStock();
			if (currentStock < quantity) {
				throw new NotEnoughStockException(product.getName());
			}
			product.setStock(product.getStock() - quantity);
		});
		productDAO.saveAll(list.keySet());
		list.clear();
	}

	@Override
	public final Set<String> getClassFields() {
		return invalidSortingFields;
	}

	@Override
	public Page<Product> getProductsByCategory(Integer id, Pageable pageable) {
		return productDAO.findByCategoryId(id, pageable);
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
