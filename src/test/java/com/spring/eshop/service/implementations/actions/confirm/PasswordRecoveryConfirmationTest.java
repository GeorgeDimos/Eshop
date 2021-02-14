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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordRecoveryConfirmationTest {

	@Mock
	UserDAO userDAO;
	@Mock
	ConfirmationTokenDAO confirmationTokenDAO;
	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	PasswordRecoveryConfirmation confirmation;

	ConfirmationToken token;
	User user;
	String password;

	@BeforeEach
	void setUp() {
		user = new User(1, "u", "old_password", false, null, null, null);
		token = new ConfirmationToken(1, "test", null, user);
		password = "new_password";
	}

	@Test
	void execute() {
		given(confirmationTokenDAO.findByToken("validToken"))
				.willReturn(Optional.of(token));

		confirmation.execute("validToken", password);

		assertNotEquals("old_password", user.getPassword());
		assertEquals(passwordEncoder.encode(password), user.getPassword());
		verify(userDAO).save(any(User.class));
		verify(confirmationTokenDAO).deleteByToken("validToken");
	}

	@Test
	void executeInvalidToken() {

		given(confirmationTokenDAO.findByToken("invlalidToken"))
				.willReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> {
			confirmation.execute("invlalidToken", password);
		});

		assertEquals("old_password", user.getPassword());
		verify(userDAO, never()).save(any(User.class));
		verify(confirmationTokenDAO, never()).deleteByToken("test");
	}
}
