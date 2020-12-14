package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Product;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;

@Service
public class ProductService extends BasicServices<Product, Integer> implements IProductService {

	private static final Set<String> invalidSortingFields = Set.of("images", "category", "description");

	private final ProductDAO productDAO;
	private final IOrderService orderService;

	@Autowired
	public ProductService(ProductDAO productDAO, IOrderService orderService) {
		this.productDAO = productDAO;
		this.orderService = orderService;
	}

	@Transactional
	@Override
	public void buyItems(Map<Product, Integer> list) {
		list.forEach((product, quantity) -> {
			Product currentProduct = productDAO.findById(product.getId()).orElseThrow();
			if (currentProduct.getStock() < quantity) {
				throw new NotEnoughStockException(product.getName());
			}
			product.setStock(product.getStock() - quantity);
		});

		orderService.createOrder(list);

		productDAO.saveAll(list.keySet());
		list.clear();
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
