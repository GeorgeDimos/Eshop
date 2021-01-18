package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RegistrationConfirmationTest {

	RegistrationConfirmation confirmation;

	ConfirmationToken token;

	UserDAO userDAO;
	ConfirmationTokenDAO confirmationTokenDAO;
	User user;

	@BeforeEach
	void setUp() {
		user = new User(1, "u", "p", false, null, null, null);
		token = new ConfirmationToken(1, "test", null, user);
		confirmation = new RegistrationConfirmation("test");

		userDAO = mock(UserDAO.class);
		confirmationTokenDAO = mock(ConfirmationTokenDAO.class);
	}

	@Test
	void execute() {

		given(confirmationTokenDAO.findByToken("test"))
				.willReturn(Optional.of(token));

		confirmation.execute(userDAO, confirmationTokenDAO);

		assertTrue(user.getEnabled());
		verify(userDAO).save(any(User.class));
		verify(confirmationTokenDAO).deleteByToken("test");
	}

	@Test
	void executeInvalidToken() {

		given(confirmationTokenDAO.findByToken("test"))
				.willReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> {
			confirmation.execute(userDAO, confirmationTokenDAO);
		});

		assertFalse(user.getEnabled());
		verify(userDAO, never()).save(any(User.class));
		verify(confirmationTokenDAO, never()).deleteByToken("test");
	}
}