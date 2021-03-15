package com.spring.eshop.rest;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.rest.views.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.spring.eshop.rest.CategoryController.CategoryNotFoundException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductDAO productDAO;
	private final CategoryDAO categoryDAO;
	private final ProductModelAssembler assembler;

	@Autowired
	public ProductController(ProductDAO productDAO, CategoryDAO categoryDAO, ProductModelAssembler assembler) {
		this.productDAO = productDAO;
		this.categoryDAO = categoryDAO;
		this.assembler = assembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<ProductView>> getProducts() {
		return CollectionModel.of(productDAO.findAll().stream()
										.map(ProductView::new)
										.map(assembler::toModel)
										.collect(Collectors.toList())
						, linkTo(methodOn(ProductController.class).getProducts()).withSelfRel()
		);
	}

	@GetMapping("/{id}")
	public EntityModel<ProductView> getProduct(@PathVariable int id) {
		Product product = productDAO.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		return assembler.toModel(new ProductView(product));
	}

	@PostMapping
	public ResponseEntity<EntityModel<ProductView>> addProduct(@Valid @RequestBody ProductView productView) {
		int categoryId = productView.categoryId;
		Category category = categoryDAO.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
		Product product = productDAO.save(ProductView.toProduct(productView, category));
		EntityModel<ProductView> entityModel = assembler.toModel(new ProductView(product));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public EntityModel<ProductView> editProduct(@PathVariable int id, @Valid @RequestBody ProductView productView) {
		Product product = productDAO.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		int newCategoryId = productView.categoryId;
		Category category = categoryDAO.findById(newCategoryId).orElseThrow(() -> new CategoryNotFoundException(newCategoryId));
		ProductView.updateProduct(product, category, productView);
		return assembler.toModel(new ProductView(productDAO.save(product)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteProduct(@PathVariable int id) {
		productDAO.deleteById(id);
	}


	private class ProductNotFoundException extends RuntimeException {
		public ProductNotFoundException(int id) {
			super(String.valueOf(id));
		}
	}
}

@Component
class ProductModelAssembler implements RepresentationModelAssembler<ProductView, EntityModel<ProductView>> {

	@Override
	public EntityModel<ProductView> toModel(ProductView entity) {
		return EntityModel.of(entity
						, linkTo(methodOn(ProductController.class).getProduct(entity.id)).withSelfRel()
						, linkTo(methodOn(ProductController.class).getProducts()).withRel("products")
		);
	}
}

