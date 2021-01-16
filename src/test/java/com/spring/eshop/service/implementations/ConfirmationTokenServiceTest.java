package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import com.spring.eshop.entity.ConfirmationToken;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

	@Mock
	ConfirmationTokenDAO dao;

	@InjectMocks
	ConfirmationTokenService service;

	@Test
	void createConfirmationToken() {
		doNothing().when(dao).deleteByUser(any(User.class));
		when(dao.save(any(ConfirmationToken.class))).thenReturn(any(ConfirmationToken.class));

		String result = service.createConfirmationToken(new User());
		assertThat(result).isNotBlank();
	}

	@Test
	void createConfirmationTokenNullUser() {
		doNothing().when(dao).deleteByUser(null);
		doThrow(new RuntimeException()).when(dao).save(any(ConfirmationToken.class));
		assertThrows(RuntimeException.class, () -> {
			service.createConfirmationToken(null);
		});
	}

	@Test
	void isValid() {
		when(dao.existsByToken(anyString())).thenReturn(true);
		assertThat(service.isValid("anyString")).isTrue();
	}

	@Test
	void isNotValid() {
		when(dao.existsByToken(anyString())).thenReturn(false);
		assertThat(service.isValid("anyString")).isFalse();
	}
}