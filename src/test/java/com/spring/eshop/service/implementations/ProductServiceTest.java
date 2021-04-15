package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ProductDAO;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductDAO dao;

	@InjectMocks
	ProductService service;

	@Test
	void getProduct() {
		given(dao.findById(gt(0))).willReturn(Optional.of(mock(Product.class)));
		Product result = service.getProduct(1);
		assertThat(result).isNotNull();
	}

	@Test
	void getProductFail() {
		doThrow(new NoSuchElementException()).when(dao).findById(gt(0));
		assertThrows(NoSuchElementException.class, () -> {
			service.getProduct(1);
		});
	}

	@Test
	void getProducts() {
		Page<Product> products = new PageImpl<>(List.of(
						mock(Product.class),
						mock(Product.class)
		));
		given(dao.findDistinctBy(any(Pageable.class))).willReturn(products);
		Page<Product> result = service.getProducts(mock(Pageable.class));
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
	}

	@Test
	void getProductsByName() {
		Pageable pageable = mock(Pageable.class);
		Page<Product> products = new PageImpl<>(List.of(
						mock(Product.class),
						mock(Product.class)
		));
		given(dao.findDistinctByNameContaining(anyString(), any(Pageable.class))).willReturn(products);
		Page<Product> result = service.getProductsByName("name", pageable);
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
	}
}