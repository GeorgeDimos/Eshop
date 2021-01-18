package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PasswordRecoveryConfirmationTest {

	PasswordRecoveryConfirmation confirmation;

	ConfirmationToken token;

	UserDAO userDAO;
	ConfirmationTokenDAO confirmationTokenDAO;
	User user;

	@BeforeEach
	void setUp() {
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		user = new User(1, "u", "p", false, null, null, null);
		token = new ConfirmationToken(1, "test", null, user);
		confirmation = new PasswordRecoveryConfirmation("test", "newpassword", encoder);
		userDAO = mock(UserDAO.class);
		confirmationTokenDAO = mock(ConfirmationTokenDAO.class);
	}

	@Test
	void execute() {
		given(confirmationTokenDAO.findByToken("test"))
				.willReturn(Optional.of(token));

		confirmation.execute(userDAO, confirmationTokenDAO);

		assertNotEquals("p", user.getPassword());
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

		assertEquals("p", user.getPassword());
		verify(userDAO, never()).save(any(User.class));
		verify(confirmationTokenDAO, never()).deleteByToken("test");
	}
}