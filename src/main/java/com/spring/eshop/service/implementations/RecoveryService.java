package com.spring.eshop.service.implementations;

import com.spring.eshop.service.implementations.actions.confirm.PasswordRecoveryConfirmation;
import com.spring.eshop.service.implementations.actions.request.PasswordRecoveryEmail;
import com.spring.eshop.service.implementations.actions.request.ResendActivationEmail;
import com.spring.eshop.service.interfaces.IRecoveryService;
import org.springframework.stereotype.Service;

@Service
public class RecoveryService implements IRecoveryService {

	private final PasswordRecoveryConfirmation recoveryConfirmation;
	private final ResendActivationEmail activationEmail;
	private final PasswordRecoveryEmail passwordRecoveryEmail;

	public RecoveryService(PasswordRecoveryConfirmation recoveryConfirmation, ResendActivationEmail activationEmail, PasswordRecoveryEmail passwordRecoveryEmail) {
		this.recoveryConfirmation = recoveryConfirmation;
		this.activationEmail = activationEmail;
		this.passwordRecoveryEmail = passwordRecoveryEmail;
	}

	public void resendActivationEmail(String username, String email) {
		activationEmail.execute(username, email);
	}

	public void passwordRecoveryEmail(String username, String email) {
		passwordRecoveryEmail.execute(username, email);
	}

	public void passwordRecoveryConfirmation(String token, String password) {
		recoveryConfirmation.execute(token, password);
	}
}
