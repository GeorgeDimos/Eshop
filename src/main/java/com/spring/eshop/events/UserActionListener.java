package com.spring.eshop.events;

import com.spring.eshop.entity.Order;
import com.spring.eshop.entity.OrderItem;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UserActionListener {

	private final IConfirmationTokenService confirmationTokenService;
	private final JavaMailSender mailSender;

	@Autowired
	public UserActionListener(IConfirmationTokenService confirmationTokenService, JavaMailSender mailSender) {
		this.confirmationTokenService = confirmationTokenService;
		this.mailSender = mailSender;
	}

	@EventListener(classes = ActivationRequiredEvent.class)
	public void sendUserRegistrationEmail(ActivationRequiredEvent event) {
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
		SimpleMailMessage simpleMailMessage = createOrderDetailsEmail(event.getEmail(), event.getOrder());
		mailSender.send(simpleMailMessage);
	}

	private SimpleMailMessage createOrderDetailsEmail(String email, Order order) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("New Order - Spring Eshop");
		simpleMailMessage.setTo(email);
		String body = "Hello!\n\n" +
						"Thank you for your order!\n\n" +
						"Order's contents:\n";
		for (OrderItem item : order.getItems()) {
			body += (item.getProduct().getName() + ": " + item.getQuantity()) + " ordered\n";
		}
		simpleMailMessage.setText(body);
		return simpleMailMessage;
	}

	private SimpleMailMessage createRegistrationEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Confirm User Registration for Spring Eshop");
		simpleMailMessage.setTo(email);
		String server = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		simpleMailMessage.setText("Click the following link to confirm your account\n\n"
				+ server + "/register/" + token);
		return simpleMailMessage;
	}

	private SimpleMailMessage createPasswordRecoveryEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Password Recovery for Spring Eshop");
		simpleMailMessage.setTo(email);
		String server = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		simpleMailMessage.setText("Click the following link to recover your password\n\n"
				+ server + "/recover/" + token);
		return simpleMailMessage;
	}
}
