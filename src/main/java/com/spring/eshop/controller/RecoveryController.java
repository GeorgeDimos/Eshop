package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.actions.ActionService;
import com.spring.eshop.service.implementations.actions.confirm.PasswordRecoveryConfirmation;
import com.spring.eshop.service.implementations.actions.request.PasswordRecoveryEmail;
import com.spring.eshop.service.implementations.actions.request.ResendActivationEmail;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

	private final IConfirmationTokenService confirmationTokenService;
	private final ActionService actionService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RecoveryController(IConfirmationTokenService confirmationTokenService, ActionService actionService, PasswordEncoder passwordEncoder) {
		this.confirmationTokenService = confirmationTokenService;
		this.actionService = actionService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/activationEmail")
	public String getActivationEmail() {
		return "resendActivationEmail";
	}

	@PostMapping("/activationEmail")
	public String resendActivationEmail(@RequestParam String username,
										@RequestParam String email,
										Model model,
										RedirectAttributes redirectAttributes) {

		try {

			actionService.request(new ResendActivationEmail(username, email));
		} catch (InvalidUserInfoException ex) {
			model.addAttribute("error", ex.getMessage());
			return "resendActivationEmail";
		}
		redirectAttributes.addFlashAttribute("success",
				"Check your email for the activation link");
		return "redirect:/login";
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

			actionService.request(new PasswordRecoveryEmail(username, email));
		} catch (InvalidUserInfoException ex) {
			model.addAttribute("error", ex.getMessage());
			return "recover-password";
		}
		redirectAttributes.addFlashAttribute("success",
				"Check your email for password recovery instructions");
		return "redirect:/login";
	}

	@GetMapping("/{token}")
	public String getPasswordInputPage(@PathVariable String token, Model model) {
		if (!confirmationTokenService.isValid(token)) {
			return "redirect:/error";
		}
		model.addAttribute("user", new User());
		return "new-password";
	}

	@PostMapping("/{token}")
	public String changePassword(@PathVariable("token") String token,
								 @Valid @ModelAttribute("user") User user,
								 BindingResult bindingResultUser,
								 RedirectAttributes redirectAttributes) {

		if (bindingResultUser.hasFieldErrors("password")) {
			return "new-password";
		}
		actionService.confirm(new PasswordRecoveryConfirmation(token, user.getPassword(), passwordEncoder));
		redirectAttributes.addFlashAttribute("success", "Password successfully changed");
		return "redirect:/login";
	}
}