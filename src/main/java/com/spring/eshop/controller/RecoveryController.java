package com.spring.eshop.controller;

import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import com.spring.eshop.service.interfaces.IUserConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

	private final IUserConfirmationService userConfirmationService;
	private final IConfirmationTokenService confirmationTokenService;

	@Autowired
	public RecoveryController(IUserConfirmationService userConfirmationService, IConfirmationTokenService confirmationTokenService) {
		this.userConfirmationService = userConfirmationService;
		this.confirmationTokenService = confirmationTokenService;
	}

	@GetMapping("/activationEmail")
	public String getActivationEmail(Model model) {
		model.addAttribute("title", "Resend Activation Email");
		return "recover-password";
	}

	@PostMapping("/activationEmail")
	public String resendActivationEmail(@RequestParam String username,
										@RequestParam String email,
										Model model,
										RedirectAttributes redirectAttributes) {

		try {
			userConfirmationService.resendActivationEmail(username, email);
			redirectAttributes.addFlashAttribute("recovery",
					"Check your email for the activation link");
			return "redirect:/login";
		} catch (InvalidUserInfoException ex) {
			model.addAttribute("title", "Resend Activation Email");
			model.addAttribute("error", ex.getMessage());
			return "recover-password";
		}
	}

	@GetMapping("/password")
	public String getPasswordRecovery(Model model) {
		model.addAttribute("title", "Recover Password");
		return "recover-password";
	}

	@PostMapping("/password")
	public String recoverPassword(@RequestParam String username,
								  @RequestParam String email,
								  Model model,
								  RedirectAttributes redirectAttributes) {

		try {
			userConfirmationService.sendPasswordRecoveryEmail(username, email);
			redirectAttributes.addFlashAttribute("recovery",
					"Check your email for password recovery instructions");
			return "redirect:/login";
		} catch (InvalidUserInfoException ex) {
			model.addAttribute("title", "Recover Password");
			model.addAttribute("error", ex.getMessage());
			return "recover-password";
		}
	}

	@GetMapping("/{token}")
	public String getPasswordInputPage(@PathVariable String token) {
		confirmationTokenService.getConfirmationTokenByToken(token);
		return "new-password";
	}

	@PostMapping("/{token}")
	public String changePassword(@PathVariable("token") String token,
								 @RequestParam("password") String password,
								 RedirectAttributes redirectAttributes) {
		userConfirmationService.confirmPasswordChange(token, password);
		redirectAttributes.addFlashAttribute("recovery", "Password successfully changed");
		return "redirect:/login";
	}
}