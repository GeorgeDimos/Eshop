package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	CategoryDAO dao;

	@InjectMocks
	CategoryService service;

	@Test
	void getCategories() {
		Pageable pageableStub = mock(Pageable.class);
		Page<Category> page = new PageImpl(List.of(
				mock(Category.class),
				mock(Category.class)
		));
		given(dao.findDistinctBy(pageableStub)).willReturn(page);
		Page<Category> result = service.getCategories(pageableStub);

		verify(dao).findDistinctBy(pageableStub);
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(2);
	}

	@Test
	void getProductsOfCategory() {
		Pageable pageableStub = mock(Pageable.class);
		Category c1 = mock(Category.class);
		Page<Product> page = new PageImpl(List.of(
				new Product(1, "test product", "test product description", 3, 10.5, null, c1),
				new Product(2, "test product 2", "test product description 2", 1, 15, null, c1)
		));
		given(dao.getProductsOfCategory(1, pageableStub)).willReturn(page);
		Page<Product> result = service.getProductsOfCategory(1, pageableStub);

		verify(dao).getProductsOfCategory(1, pageableStub);
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(2);
	}
}