package com.spring.eshop.events;

import com.spring.eshop.entity.Order;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserActionListener {

	private final IConfirmationTokenService confirmationTokenService;
	private final JavaMailSender mailSender;

	@Autowired
	public UserActionListener(IConfirmationTokenService confirmationTokenService, JavaMailSender mailSender) {
		this.confirmationTokenService = confirmationTokenService;
		this.mailSender = mailSender;
	}

	@EventListener(classes = UserRegistrationEvent.class)
	public void sendUserRegistrationEmail(UserRegistrationEvent event) {
		String token = confirmationTokenService.createConfirmationToken(event.getUser());
		SimpleMailMessage simpleMailMessage = createRegistrationEmail(event.getEmail(), token);
		mailSender.send(simpleMailMessage);
	}

	@EventListener(classes = PasswordRecoveryEvent.class)
	public void sendPasswordRecoveryEmail(PasswordRecoveryEvent event) {
		String token = confirmationTokenService.createConfirmationToken(event.getUser());
		SimpleMailMessage simpleMailMessage = createPasswordRecoveryEmail(event.getEmail(), token);
		mailSender.send(simpleMailMessage);
	}

	@EventListener(classes = OrderReceivedEvent.class)
	public void sendOrderDetailsEmail(OrderReceivedEvent event) {
		SimpleMailMessage simpleMailMessage = createOrderDetailsEmail(event.getOrder(), event.getEmail());
		mailSender.send(simpleMailMessage);
	}

	private SimpleMailMessage createOrderDetailsEmail(Order order, String email) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("New Order - Spring Eshop");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setText("Hello " + order.getUser().getUsername() + "!\n\n" +
				"Thank you for your order!");
		return simpleMailMessage;
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
