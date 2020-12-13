package com.spring.eshop.controller;

import com.spring.eshop.service.implementations.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("recover")
public class RecoveryController {

	private final RegisterUserService registerUserService;

	@Autowired
	public RecoveryController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	@GetMapping("/password")
	public String recoverPassword() {
		return "recover-password";
	}

	@PostMapping("/password")
	public String recoverPasswordPost(@RequestParam String username,
									  @RequestParam String email,
									  Model model,
									  RedirectAttributes redirectAttributes) {
		try {
			registerUserService.recoverPassword(username, email);
		} catch (RuntimeException ex) {
			model.addAttribute("error", ex.getMessage());
			return "recover-password";
		}
		redirectAttributes.addFlashAttribute("recovery",
				"Check your email for password recovery instructions");
		return "redirect:/login";
	}

	@GetMapping("{token}")
	public String newPasswordInput(@PathVariable("token") String token, Model model) {
		model.addAttribute("user", registerUserService.getUserFromToken(token));
		return "new-password";
	}

	@PostMapping("{token}")
	public String changePassword(@PathVariable("token") String token,
								 @RequestParam("password") String password,
								 RedirectAttributes redirectAttributes) {
		registerUserService.changePassword(token, password);
		redirectAttributes.addFlashAttribute("recovery", "Password successfully changed");
		return "redirect:/login";
	}
}
