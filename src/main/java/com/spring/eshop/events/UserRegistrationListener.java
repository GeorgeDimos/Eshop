package com.spring.eshop.events;

import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

	private final IConfirmationTokenService confirmationTokenService;
	private final JavaMailSender mailSender;

	@Autowired
	public UserRegistrationListener(IConfirmationTokenService confirmationTokenService, JavaMailSender mailSender) {
		this.confirmationTokenService = confirmationTokenService;
		this.mailSender = mailSender;
	}

	@EventListener
	public void sentUserRegistrationEmail(UserRegistrationEvent event) {
		String token = confirmationTokenService.createConfirmationToken(event.getUser());
		SimpleMailMessage simpleMailMessage = createRegistrationEmail(event.getEmail(), token);
		mailSender.send(simpleMailMessage);
	}

	@EventListener
	public void sentPasswordRecoveryEmail(PasswordRecoveryEvent event) {
		String token = confirmationTokenService.createConfirmationToken(event.getUser());
		SimpleMailMessage simpleMailMessage = createPasswordRecoveryEmail(event.getEmail(), token);
		mailSender.send(simpleMailMessage);
	}

	private SimpleMailMessage createRegistrationEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Confirm User Registration for Spring Eshop");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setText("Click the following link to confirm your account\n\nhttp://localhost:8080/register/" + token);
		return simpleMailMessage;
	}

	private SimpleMailMessage createPasswordRecoveryEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Password Recovery for Spring Eshop");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setText("Click the following link to recover your password\n\nhttp://localhost:8080/recover/" + token);
		return simpleMailMessage;
	}
}
