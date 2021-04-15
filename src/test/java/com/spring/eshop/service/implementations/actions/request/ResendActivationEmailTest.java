package com.spring.eshop.service.implementations.actions.request;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.InvalidUserInfoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResendActivationEmailTest {

	@Mock
	UserDAO userDAO;
	@Mock
	ApplicationEventPublisher publisher;

	@InjectMocks
	ResendActivationEmail activationEmail;

	UserInfo userInfo;

	@BeforeEach
	void setUp() {
		userInfo = mock(UserInfo.class);
	}

	@Test
	void execute() {
		User inactiveUser = new User(1, "u", "p", false, null, userInfo, null);
		given(userDAO.findByUsernameAndUserInfoEmail(inactiveUser.getUsername(), userInfo.getEmail()))
						.willReturn(Optional.of(inactiveUser));

		activationEmail.execute(inactiveUser.getUsername(), userInfo.getEmail());

		verify(publisher).publishEvent(any(ApplicationEvent.class));
	}

	@Test
	void executeUserDoesNotExist() {
		given(userDAO.findByUsernameAndUserInfoEmail(anyString(), anyString()))
				.willReturn(Optional.empty());

		assertThrows(InvalidUserInfoException.class, () -> {
			activationEmail.execute(anyString(), anyString());
		});

		verify(publisher, never()).publishEvent(any(ApplicationEvent.class));
	}

	@Test
	void executeUserIsAlreadyEnabled() {
		User enabledUser = new User(1, "u", "p", true, null, userInfo, null);
		given(userDAO.findByUsernameAndUserInfoEmail(enabledUser.getUsername(), userInfo.getEmail()))
						.willReturn(Optional.of(enabledUser));

		assertThrows(InvalidUserInfoException.class, () -> {
			activationEmail.execute(enabledUser.getUsername(), userInfo.getEmail());
		});

		verify(publisher, never()).publishEvent(any(ApplicationEvent.class));
	}
}
