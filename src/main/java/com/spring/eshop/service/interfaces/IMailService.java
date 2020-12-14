package com.spring.eshop.service.interfaces;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

public interface IMailService {
	@Bean
	JavaMailSender getJavaMailSender();
}
