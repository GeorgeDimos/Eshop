package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEvent;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PasswordRecoveryEmailTest {


	@Mock
	UserDAO userDAO;

	User user;
	UserInfo userInfo;
	PasswordRecoveryEmail recoveryEmail;

	@BeforeEach
	void setUp() {
		recoveryEmail = new PasswordRecoveryEmail("u", "email@mail.gr");
		userInfo = new UserInfo(1, "f", "l", "email@mail.gr", null);
	}

	@Test
	void execute() {
		user = new User(1, "u", "p", true, null, userInfo, null);
		given(userDAO.findByUsernameAndUserInfoEmail(user.getUsername(), userInfo.getEmail()))
				.willReturn(Optional.of(user));

		ApplicationEvent result = recoveryEmail.execute(userDAO);
		assertNotNull(result);
	}

	@Test
	void executeUserDoesNotExist() {
		given(userDAO.findByUsernameAndUserInfoEmail(anyString(), anyString()))
				.willReturn(Optional.empty());

		assertThrows(InvalidUserInfoException.class, () -> {
			recoveryEmail.execute(userDAO);
		});

	}

	@Test
	void executeUserIsNotYetEnabled() {
		user = new User(1, "u", "p", false, null, userInfo, null);
		given(userDAO.findByUsernameAndUserInfoEmail(user.getUsername(), userInfo.getEmail()))
				.willReturn(Optional.of(user));

		assertThrows(InvalidUserInfoException.class, () -> {
			recoveryEmail.execute(userDAO);
		});

	}

}