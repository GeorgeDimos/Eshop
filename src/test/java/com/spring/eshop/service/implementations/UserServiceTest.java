package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserDAO userDAO;

	@InjectMocks
	UserService service;

	@Test
	void loadUserByUsername() {
		User user = new User(1, "username", "password", true, Collections.emptyList(), null, Collections.emptySet());
		given(userDAO.findByUsername(anyString())).willReturn(Optional.of(user));
		UserDetails result = service.loadUserByUsername(user.getUsername());
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("username");
	}

	@Test
	void loadUserByUsernameFails() {
		given(userDAO.findByUsername(anyString())).willReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("nameNotFound");
		});
	}

	@Test
	void deleteUser() {
		User user = new User(1, "username", "password", true, Collections.emptyList(), null, Collections.emptySet());
		service.delete(user);
		verify(userDAO).delete(user);
	}

	@Test
	void deleteNull() {
		doThrow(new IllegalArgumentException()).when(userDAO).delete(null);
		assertThrows(IllegalArgumentException.class, () -> {
			service.delete(null);
		});
	}
}