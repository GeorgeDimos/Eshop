package com.spring.eshop.events;

import com.spring.eshop.service.implementations.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

	private final ConfirmationTokenService confirmationTokenService;

	private final JavaMailSender mailSender;

	@Autowired
	public UserRegistrationListener(ConfirmationTokenService confirmationTokenService, JavaMailSender mailSender) {
		this.confirmationTokenService = confirmationTokenService;
		this.mailSender = mailSender;
	}

	@EventListener
	public void sentUserRegistrationEmail(UserRegistrationEvent event) {
		String token = confirmationTokenService.createAndReturnConfirmationToken(event.getUser());
		SimpleMailMessage simpleMailMessage = createRegistrationEmail(event.getUserInfo().getEmail(), token);
		mailSender.send(simpleMailMessage);
	}

	private SimpleMailMessage createRegistrationEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Confirm User Registration for Spring Eshop");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setText("Click the following link to confirm your account\nhttp://localhost:8080/register/" + token);
		return simpleMailMessage;
	}
}
