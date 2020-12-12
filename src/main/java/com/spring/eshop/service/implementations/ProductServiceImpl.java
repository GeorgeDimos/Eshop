package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.OrderItemDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.entity.Product;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.security.UserPrinciple;
import com.spring.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;

@Service
public class ProductServiceImpl extends BasicServicesImpl<Product, Integer> implements ProductService {

	private static final Set<String> invalidSortingFields = Set.of("images", "category", "description");

	private final ProductDAO productDAO;

	private final OrderDAO orderDAO;

	private final OrderItemDAO orderItemDAO;

	@Autowired
	public ProductServiceImpl(ProductDAO productDAO, OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
		this.productDAO = productDAO;
		this.orderDAO = orderDAO;
		this.orderItemDAO = orderItemDAO;
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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple currentUser = (UserPrinciple) authentication.getPrincipal();

		Order order = new Order();
		order.setCustomerId(currentUser.getUserId());
		orderDAO.save(order);

		list.forEach((product, quantity) -> {
			OrderItem item = new OrderItem(product, quantity, order.getId());
			orderItemDAO.save(item);
		});

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
