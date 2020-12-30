package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

    Page<Category> getCategories(Pageable pageable);

    Page<Product> getProductsOfCategory(int id, Pageable pageable);
}
