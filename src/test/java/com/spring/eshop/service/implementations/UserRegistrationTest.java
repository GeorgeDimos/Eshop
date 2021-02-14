package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.actions.confirm.RegistrationConfirmation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRegistrationTest {

	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	UserDAO userDAO;
	@Mock
	ApplicationEventPublisher publisher;
	@Mock
	RegistrationConfirmation registrationConfirmation;
	@InjectMocks
	UserRegistration userRegistration;

	User user;
	UserInfo userInfo;

	@BeforeEach
	void setUp() {
		user = new User(1, "u", "text_password", true, null, null, null);
		userInfo = new UserInfo(1, "f", "l", "email@mail.gr", null);
	}

	@Test
	void register() {
		given(userDAO.existsByUsernameOrUserInfoEmail(anyString(), anyString()))
				.willReturn(false);
		userRegistration.register(user, userInfo);
		assertNotEquals("text_password", user.getPassword());
		assertEquals(passwordEncoder.encode("text_password"), user.getPassword());
		verify(userDAO).save(user);
		verify(publisher).publishEvent(any(ActivationRequiredEvent.class));
	}

	@Test
	void registerButUserExists() {
		given(userDAO.existsByUsernameOrUserInfoEmail(anyString(), anyString()))
				.willReturn(true);
		assertThrows(UserAlreadyExistsException.class, () -> {
			userRegistration.register(user, userInfo);
		});

		verify(userDAO, never()).save(any(User.class));
		verify(publisher, never()).publishEvent(any(ActivationRequiredEvent.class));
	}

	@Test
	void confirmRegistration() {
		userRegistration.confirm("token");
		verify(registrationConfirmation).execute("token", null);
	}
}