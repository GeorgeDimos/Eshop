package com.spring.eshop.rest.views;

import com.spring.eshop.entity.Category;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Relation(collectionRelation = "categories")
public class CategoryView {
	public int id;
	@NotBlank
	public String name;
	public List<ProductView> products;

	public CategoryView() {
	}

	public CategoryView(Category category) {
		name = category.getName();
		products = category.getProducts().stream()
						.map(ProductView::new)
						.collect(Collectors.toList());
		id = category.getId();
	}

	public Category translateToCategory() {
		Category category = new Category();
		category.setName(name);
		category.setProducts(new ArrayList<>());
		return category;
	}
}
