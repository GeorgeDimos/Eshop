package com.spring.eshop.controller;

import com.spring.eshop.entity.ShoppingCart;
import com.spring.eshop.exceptions.NotEnoughStockException;
import com.spring.eshop.service.implementations.AuthGroupService;
import com.spring.eshop.service.interfaces.IOrderService;
import com.spring.eshop.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final IProductService productService;
	private final IOrderService orderService;
	private final ShoppingCart shoppingCart;
	private final AuthGroupService authGroupService;

	@Autowired
	public CheckoutController(IProductService productService, IOrderService orderService, ShoppingCart shoppingCart, AuthGroupService authGroupService) {
		this.productService = productService;
		this.orderService = orderService;
		this.shoppingCart = shoppingCart;
		this.authGroupService = authGroupService;
	}

	@GetMapping
	public String checkout(Model model) {
		model.addAttribute("shoppingList", shoppingCart.getShoppingCart());
		return "/checkout";
	}

	@PostMapping
	public String buyProducts(@RequestParam(required = false) String confirm) {
		if (confirm != null && confirm.equals("Purchase")) {
			productService.buyItems(shoppingCart.getShoppingCart());
			orderService.createOrder(authGroupService.getCurrentUser(), shoppingCart.getShoppingCart());
			shoppingCart.clear();
		}

		return "redirect:/products";
	}

	@ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStockInOrder(NotEnoughStockException ex, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("notEnoughStockError",
				"We don't have enough stock of " + ex.getProductName() + " anymore. Please reduce the quantity or remove it.");
		return "redirect:/cart";
	}
}
