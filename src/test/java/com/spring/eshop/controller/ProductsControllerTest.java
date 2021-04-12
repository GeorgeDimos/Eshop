package com.spring.eshop.controller;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Image;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.service.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

	@MockBean
	IProductService productService;

	@MockBean
	ShoppingCart shoppingCart;

	@Autowired
	MockMvc mockMvc;

	Product product;
	Category c1;

	@BeforeEach
	void setUp() {
		c1 = mock(Category.class);
		product = product = new Product(1,
				"test_product_1",
				"test_description_1",
				10, 10, List.of(new Image()), c1);
	}

	@Test
	void getProducts() throws Exception {

		Page<Product> pageProducts = new PageImpl<>(List.of(
				product,
				new Product(
						2, "test_product_2",
						"test_description_2",
						10, 0.10, null, c1
				)));
		given(productService.getProducts(any(Pageable.class))).willReturn(pageProducts);

		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(view().name("products"))
				.andExpect(model().attributeExists("products"));
	}

	@Test
	void getProduct() throws Exception {
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(get("/products/{id}", product.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("productPage"))
				.andExpect(model().attributeExists("product"));
	}

	@Test
	void addProductToCartGoToProducts() throws Exception {
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(post("/products/{id}", product.getId())
				.param("quantity", "2")
				.with(csrf())
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));

		then(shoppingCart).should().add(any(Product.class), gt(0));
	}

	@Test
	void addProductToCartGoToCart() throws Exception {
		given(productService.getProduct(gt(0))).willReturn(product);
		mockMvc.perform(post("/products/{id}", product.getId())
				.param("quantity", "2")
				.param("goToCart", "Ok")
				.with(csrf())
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cart"));

		then(shoppingCart).should().add(any(Product.class), gt(0));
	}
}