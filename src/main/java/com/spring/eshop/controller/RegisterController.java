package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserDetails;
import com.spring.eshop.service.implementations.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private RegisterUserService registerUserService;

	@Autowired
	public RegisterController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	@GetMapping
	public String register(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("userdetails", new UserDetails());
		return "register";
	}

	@PostMapping
	public String registerNewUser(@Valid @ModelAttribute("user") User user,
								  BindingResult bindingResultUser,
								  @Valid @ModelAttribute("userdetails") UserDetails userDetails,
								  BindingResult bindingResultUserDetails) {

		if (bindingResultUser.hasErrors() || bindingResultUserDetails.hasErrors()) {
			return "/register";
		}
		registerUserService.registerNewUser(user, userDetails);
		return "redirect:/products";
	}
}
