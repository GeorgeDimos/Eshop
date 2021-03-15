package com.spring.eshop.rest.views;

import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Image;
import com.spring.eshop.entity.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductView {
	public int id;
	@NotNull
	public String name;
	public String description;
	@Min(0)
	public int stock;
	@Min(0)
	public double price;
	@NotNull
	public int categoryId;
	public List<Image> images;

	public ProductView() {
	}

	public ProductView(Product product) {
		id = product.getId();
		name = product.getName();
		description = product.getDescription();
		stock = product.getStock();
		price = product.getPrice();
		images = product.getImages();
		categoryId = product.getCategory().getId();
	}

	public static Product toProduct(ProductView productView, Category category) {
		Product product = new Product();
		product.setName(productView.name);
		product.setDescription(productView.description);
		product.setStock(productView.stock);
		product.setPrice(productView.price);
		product.setImages(productView.images);
		product.setCategory(category);
		return product;
	}

	public static void updateProduct(Product product, Category category, ProductView productView) {
		product.setName(productView.name);
		product.setCategory(category);
		product.setPrice(productView.price);
		product.setStock(productView.stock);
		product.setDescription(productView.description);
		product.setImages(productView.images);
	}
}

