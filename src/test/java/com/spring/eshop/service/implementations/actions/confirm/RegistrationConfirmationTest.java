package com.spring.eshop.service.implementations.actions.confirm;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationConfirmationTest {

	@Mock
	UserDAO userDAO;
	@Mock
	ConfirmationTokenDAO confirmationTokenDAO;

	@InjectMocks
	RegistrationConfirmation confirmation;

	User user;

	@BeforeEach
	void setUp() {
		user = new User(1, "u", "p", false, null, null, null);
	}

	@Test
	void execute() {
		given(confirmationTokenDAO.findByToken(anyString()))
						.willReturn(Optional.of(new ConfirmationToken(1, "validToken", null, user)));

		confirmation.execute("validToken", null);

		assertTrue(user.getEnabled());
		verify(userDAO).save(user);
		verify(confirmationTokenDAO).deleteByToken("validToken");
	}

	@Test
	void executeInvalidToken() {

		given(confirmationTokenDAO.findByToken(anyString()))
						.willReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> {
			confirmation.execute("invalidToken", null);
		});

		assertFalse(user.getEnabled());
		verify(userDAO, never()).save(any(User.class));
		verify(confirmationTokenDAO, never()).deleteByToken(anyString());
	}
}
