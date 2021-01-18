package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Product;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductService implements IProductService {

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
		return productDAO.findDistinctBy(pageable);
	}

	@Override
	public Page<Product> getProductsByName(String name, Pageable pageable) {
		return productDAO.findDistinctByNameContaining(name, pageable);
	}

}
