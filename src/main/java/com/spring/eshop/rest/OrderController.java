package com.spring.eshop.rest;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.entity.Order;
import com.spring.eshop.rest.views.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderDAO orderDAO;
	private final OrderModelAssembler assembler;

	@Autowired
	public OrderController(OrderDAO orderDAO, OrderModelAssembler assembler) {
		this.orderDAO = orderDAO;
		this.assembler = assembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<OrderView>> getOrders() {

		return CollectionModel.of(orderDAO.findAll().stream()
										.map(OrderView::new)
										.map(assembler::toModel)
										.collect(Collectors.toList())
						, linkTo(methodOn(OrderController.class).getOrders()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<OrderView> getOrder(@PathVariable int id) {
		Order order = orderDAO.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return assembler.toModel(new OrderView(order));
	}

	@PutMapping("/{id}")
	public EntityModel<OrderView> updateOrder(@PathVariable int id, @Valid @RequestBody OrderView orderView) {
		Order order = orderDAO.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		OrderView.updateQuantities(order, orderView);
		return assembler.toModel(new OrderView(orderDAO.save(order)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteOrder(@PathVariable int id) {
		orderDAO.deleteById(id);
	}

	private static class OrderNotFoundException extends RuntimeException {
		public OrderNotFoundException(int id) {
			super(String.valueOf(id));
		}
	}
}

@Component
class OrderModelAssembler implements RepresentationModelAssembler<OrderView, EntityModel<OrderView>> {

	@Override
	public EntityModel<OrderView> toModel(OrderView order) {
		return EntityModel.of(order
						, linkTo(methodOn(OrderController.class).getOrder(order.id)).withSelfRel()
						, linkTo(methodOn(OrderController.class).getOrders()).withRel("orders")
						, linkTo(methodOn(UserController.class).getUser(order.userId)).withRel("user")
		);
	}
}