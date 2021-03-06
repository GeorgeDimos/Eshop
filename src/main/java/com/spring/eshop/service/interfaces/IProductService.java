package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

	Page<Product> getProducts(Pageable pageable);

	Product getProduct(int id);

	Page<Product> getProductsByName(String name, Pageable pageable);
}
