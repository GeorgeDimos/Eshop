package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.user.actions.ConfirmUserRegistration;
import com.spring.eshop.service.implementations.user.requests.UserRegistration;
import com.spring.eshop.service.interfaces.IUserRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private final IUserRequests userRequests;

	@Autowired
	public RegisterController(IUserRequests userRequests) {
		this.userRequests = userRequests;
	}

	@GetMapping
	public String register(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("userInfo", new UserInfo());
		return "register";
	}

	@PostMapping
	public String registerNewUser(@Valid @ModelAttribute("user") User user,
								  BindingResult bindingResultUser,
								  @Valid @ModelAttribute("userInfo") UserInfo userInfo,
								  BindingResult bindingResultUserDetails,
								  RedirectAttributes redirectAttributes) {

		if (bindingResultUser.hasErrors() || bindingResultUserDetails.hasErrors()) {
			return "/register";
		}
		userRequests.request(new UserRegistration(user, userInfo));
		redirectAttributes.addFlashAttribute("success",
				"User registration successful. Please check your email and confirm your account.");
		return "redirect:/login";
	}

	@GetMapping("/{token}")
	public String confirmRegistration(@PathVariable("token") String token, Model model) {
		userRequests.action(new ConfirmUserRegistration(token, null));
		model.addAttribute("success",
				"Your account is confirmed. You can now login.");
		return "login";
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	private String userAlreadyExists(UserAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("userAlreadyExists", ex.getMessage());
		return "redirect:/register";
	}
}
