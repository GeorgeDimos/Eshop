package com.spring.eshop.rest;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RootController {

	@GetMapping
	public ResponseEntity<RepresentationModel> getApiInfo() {

		RepresentationModel model = new RepresentationModel();

		model.add(
						linkTo(methodOn(RootController.class).getApiInfo()).withSelfRel()
						, linkTo(methodOn(ProductController.class).getProducts()).withRel("products")
						, linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories")
						, linkTo(methodOn(UserController.class).getUsers()).withRel("users")
						, linkTo(methodOn(OrderController.class).getOrders()).withRel("orders")
		);

		return ResponseEntity.ok(model);
	}
}
