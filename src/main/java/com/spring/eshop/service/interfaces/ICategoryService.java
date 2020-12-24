package com.spring.eshop.service.interfaces;

import com.spring.eshop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

	Page<Category> getCategories(Pageable pageable);
}
