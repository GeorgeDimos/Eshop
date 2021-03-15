package com.spring.eshop.rest;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.rest.models.UserModel;
import com.spring.eshop.rest.views.OrderView;
import com.spring.eshop.rest.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserDAO userDAO;
	private final UserModelAssembler assembler;
	private final OrderModelAssembler ordersAssembler;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserDAO userDAO, UserModelAssembler assembler, OrderModelAssembler ordersAssembler, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.assembler = assembler;
		this.ordersAssembler = ordersAssembler;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public CollectionModel<EntityModel<UserView>> getUsers() {
		return CollectionModel.of(userDAO.findAll().stream()
										.map(UserView::new)
										.map(assembler::toModel)
										.collect(Collectors.toList())
						, linkTo(methodOn(UserController.class).getUsers()).withSelfRel()
		);
	}

	@GetMapping("/{id}")
	public EntityModel<UserView> getUser(@PathVariable int id) {
		User user = userDAO.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return assembler.toModel(new UserView(user));
	}

	@PostMapping
	public ResponseEntity<EntityModel<UserView>> addUser(@Valid @RequestBody UserModel userModel) {
		userModel.password = passwordEncoder.encode(userModel.password);
		User user = userDAO.save(userModel.translateToUser());
		EntityModel<UserView> entityModel = assembler.toModel(new UserView(user));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public EntityModel<UserView> updateUser(@PathVariable int id, @Valid @RequestBody UserModel userModel) {
		User user = userDAO.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		if (!userModel.password.equals(user.getPassword())) //encode password only if it was changed
			userModel.password = passwordEncoder.encode(userModel.password);
		UserModel.updateUser(user, userModel);

		return assembler.toModel(new UserView(userDAO.save(user)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteUser(@PathVariable int id) {
		userDAO.deleteById(id);
	}

	@GetMapping("/{id}/orders")
	public CollectionModel<EntityModel<OrderView>> getOrdersOfUser(@PathVariable int id) {
		User user = userDAO.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return CollectionModel.of(user.getOrders().stream()
						.map(OrderView::new)
						.map(ordersAssembler::toModel)
						.collect(Collectors.toList()), linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
	}


	private static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(int id) {
			super(String.valueOf(id));
		}
	}

}

@Component
class UserModelAssembler implements RepresentationModelAssembler<UserView, EntityModel<UserView>> {

	@Override
	public EntityModel<UserView> toModel(UserView user) {
		return EntityModel.of(user
						, linkTo(methodOn(UserController.class).getUser(user.id)).withSelfRel()
						, linkTo(methodOn(UserController.class).getUsers()).withRel("users")
						, linkTo(methodOn(UserController.class).getOrdersOfUser(user.id)).withRel("orders")
		);
	}
}