package com.spring.eshop.controller;

import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.ConfirmationTokenService;
import com.spring.eshop.service.implementations.UserConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

	private final UserConfirmationService userConfirmationService;
	private final ConfirmationTokenService confirmationTokenService;

	@Autowired
	public RecoveryController(UserConfirmationService userConfirmationService, ConfirmationTokenService confirmationTokenService) {
		this.userConfirmationService = userConfirmationService;
		this.confirmationTokenService = confirmationTokenService;
	}

	@GetMapping("/password")
	public String getPasswordRecovery() {
		return "recover-password";
	}

	@PostMapping("/password")
	public String recoverPassword(@RequestParam String username,
								  @RequestParam String email,
								  Model model,
								  RedirectAttributes redirectAttributes) {

		userConfirmationService.sentPasswordRecoveryEmail(username, email);
		redirectAttributes.addFlashAttribute("recovery",
				"Check your email for password recovery instructions");
		return "redirect:/login";
	}

	@GetMapping("/{token}")
	public String getPasswordInputPage(@PathVariable String token) {
		confirmationTokenService.getByToken(token);
		return "new-password";
	}

	@PostMapping("/{token}")
	public String changePassword(@PathVariable("token") String token,
								 @RequestParam("password") String password,
								 RedirectAttributes redirectAttributes) {
		userConfirmationService.changePassword(token, password);
		redirectAttributes.addFlashAttribute("recovery", "Password successfully changed");
		return "redirect:/login";
	}

	@ExceptionHandler(InvalidUserInfoException.class)
	private String testException(RuntimeException ex, Model model) {
		model.addAttribute("error", ex.getMessage());
		return "recover-password";
	}
}
