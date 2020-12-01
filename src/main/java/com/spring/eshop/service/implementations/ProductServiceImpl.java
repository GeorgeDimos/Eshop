package com.spring.eshop.service.implementations;

import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BasicServicesImpl<Product, Integer> implements ProductService {

	private final static Set<String> fields = Set.of("images", "category", "description");

	@Autowired
	private ProductDAO productDAO;

	@Transactional
	@Override
	public void buyItems(Map<Product, Integer> list) {

		list.forEach((product, quantity) -> {
			if (!product.buyProduct(quantity)) {
				// TODO
				// throw not enough stock exception
			}
			productDAO.saveAndFlush(product);
		});

		list.clear();
	}

	@Override
	public final Set<String> getClassFields() {
		return fields;
	}

	@Override
	public Page<Product> getProductsByCategory(Integer id, Pageable pageable) {
		return productDAO.findByCategoryId(id, filterPageable(pageable));
	}

}
