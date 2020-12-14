package com.spring.eshop.controller;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.service.implementations.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

	private final RegisterUserService registerUserService;
	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public RecoveryController(RegisterUserService registerUserService, ConfirmationTokenDAO confirmationTokenDAO) {
		this.registerUserService = registerUserService;
		this.confirmationTokenDAO = confirmationTokenDAO;
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

	@GetMapping("/{token}")
	public String getChangePasswordInputPage(@PathVariable String token) {
		if(confirmationTokenDAO.findByToken(token).isEmpty()){
			return "error";
		}
		return "new-password";
	}

	@PostMapping("/{token}")
	public String changePassword(@PathVariable("token") String token,
								 @RequestParam("password") String password,
								 RedirectAttributes redirectAttributes) {
		registerUserService.changePassword(token, password);
		redirectAttributes.addFlashAttribute("recovery", "Password successfully changed");
		return "redirect:/login";
	}
}
