package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.ActivationRequiredEvent;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
	@InjectMocks
	UserRegistration userRegistration;

	User user;
	UserInfo userInfo;

	@BeforeEach
	void setUp() {
		user = new User(1, "u", "p", true, null, null, null);
		userInfo = new UserInfo(1, "f", "l", "email@mail.gr", null);
	}

	@Test
	void execute() {
		given(userDAO.existsByUsernameOrUserInfoEmail(anyString(), anyString()))
				.willReturn(false);
		userRegistration.execute(user, userInfo);
		verify(userDAO).save(any(User.class));
		verify(publisher).publishEvent(any(ActivationRequiredEvent.class));
	}

	@Test
	void executeUserExists() {
		given(userDAO.existsByUsernameOrUserInfoEmail(anyString(), anyString()))
				.willReturn(true);
		assertThrows(UserAlreadyExistsException.class, () -> {
			userRegistration.execute(user, userInfo);
		});

		verify(userDAO, never()).save(any(User.class));
		verify(publisher, never()).publishEvent(any(ActivationRequiredEvent.class));
	}
}