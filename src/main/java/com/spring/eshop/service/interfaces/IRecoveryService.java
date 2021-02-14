package com.spring.eshop.service.interfaces;

public interface IRecoveryService {
	void resendActivationEmail(String username, String email);

	void passwordRecoveryEmail(String username, String email);

	void passwordRecoveryConfirmation(String token, String password);
}
