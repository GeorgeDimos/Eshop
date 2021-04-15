package com.spring.eshop.rest;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.rest.views.CategoryView;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryDAO categoryDAO;
	private final CategoryModelAssembler assembler;

	@Autowired
	public CategoryController(CategoryDAO categoryDAO, CategoryModelAssembler assembler) {
		this.categoryDAO = categoryDAO;
		this.assembler = assembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<CategoryView>> getCategories() {
		return CollectionModel.of(categoryDAO.findAll().stream()
										.map(CategoryView::new)
										.map(assembler::toModel)
										.collect(Collectors.toList())
						, linkTo(methodOn(CategoryController.class).getCategories()).withSelfRel()
						, linkTo(methodOn(RootController.class).getApiInfo()).withRel("api")
		);
	}

	@GetMapping("/{id}")
	public EntityModel<CategoryView> getCategory(@PathVariable int id) {
		Category category = categoryDAO.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		return assembler.toModel(new CategoryView(category));
	}

	@PostMapping
	public ResponseEntity<EntityModel<CategoryView>> addCategory(@Valid @RequestBody CategoryView categoryView) {
		Category updatedCategory = categoryDAO.save(categoryView.translateToCategory());
		EntityModel<CategoryView> entityModel = assembler.toModel(new CategoryView(updatedCategory));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public EntityModel<CategoryView> editCategory(@PathVariable int id, @Valid @RequestBody CategoryView categoryView) {
		Category category = categoryDAO.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		category.setName(categoryView.name);
		return assembler.toModel(new CategoryView(categoryDAO.save(category)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteCategory(@PathVariable int id) {
		categoryDAO.deleteById(id);
	}

	public static class CategoryNotFoundException extends RuntimeException {
		public CategoryNotFoundException(int id) {
			super(String.valueOf(id));
		}
	}
}


@Component
class CategoryModelAssembler implements RepresentationModelAssembler<CategoryView, EntityModel<CategoryView>> {

	@Override
	public EntityModel<CategoryView> toModel(CategoryView category) {
		return EntityModel.of(category
						, linkTo(methodOn(CategoryController.class).getCategory(category.id)).withSelfRel()
						, linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories")
		);
	}
}
