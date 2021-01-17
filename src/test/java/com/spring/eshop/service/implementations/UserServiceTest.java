package com.spring.eshop.service.implementations;

import com.spring.eshop.dao.AuthGroupDAO;
import com.spring.eshop.dao.UserDAO;
import com.spring.eshop.entity.AuthGroup;
import com.spring.eshop.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserDAO userDAO;
	@Mock
	AuthGroupDAO authGroupDAO;
	@InjectMocks
	UserService service;

	@Test
	void getUserById() {
		User user = mock(User.class);
		given(userDAO.findById(gt(0))).willReturn(Optional.of(user));
		User result = service.getUserById(2);
		assertThat(result).isNotNull();
	}

	@Test
	void getUserByIdFail() {
		doThrow(new NoSuchElementException()).when(userDAO).findById(gt(0));
		assertThrows(NoSuchElementException.class, () -> {
			service.getUserById(2);
		});
	}

	@Test
	void loadUserByUsername() {
		User user = mock(User.class);
		given(userDAO.findByUsername(anyString())).willReturn(Optional.of(user));
		List<AuthGroup> authGroups = List.of(mock(AuthGroup.class));
		given(authGroupDAO.findByUser(user)).willReturn(authGroups);
		UserDetails result = service.loadUserByUsername("anyString");
		assertThat(result).isNotNull();
	}

	@Test
	void loadUserByUsernameFails() {
		given(userDAO.findByUsername(anyString())).willReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("nameNotFound");
		});
		verify(authGroupDAO, never()).findByUser(any(User.class));
	}

}