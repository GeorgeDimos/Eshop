package com.spring.eshop.controller;

import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.interfaces.IUserRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

	@MockBean
	IUserRegistration userRegistration;

	@Autowired
	MockMvc mockMvc;

	@Test
	void register() throws Exception {
		mockMvc.perform(get("/register"))
						.andExpect(status().isOk())
						.andExpect(view().name("register"))
						.andExpect(model().attributeExists("user"))
						.andExpect(model().attributeExists("userInfo"));
	}

	@Test
	@WithMockUser
	void registerLoggedIn() throws Exception {
		mockMvc.perform(get("/register"))
						.andExpect(status().is4xxClientError());
	}

	@Test
	void registerNewUser() throws Exception {
		mockMvc.perform(post("/register")
						.param("username", "george")
						.param("password", "pass")
						.param("firstName", "George")
						.param("lastName", "Dimos")
						.param("email", "dimgeorge91@yahoo.gr")
						.with(csrf())
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("success"))
				.andExpect(view().name("redirect:/login"));

		verify(userRegistration).register(any(User.class), any(UserInfo.class));
	}

	@Test
	void registerNewUserFieldError() throws Exception {
		mockMvc.perform(post("/register")
				.param("username", "george")
				.param("password", "")
				.param("firstName", "George")
				.param("lastName", "Dimos")
				.param("email", "dimgeorge91")
				.with(csrf())
		)
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attributeHasFieldErrors("userInfo", "email"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));

		verify(userRegistration, never()).register(any(User.class), any(UserInfo.class));
	}

	@Test
	void registerNewUserFailureUserExists() throws Exception {
		doThrow(new UserAlreadyExistsException("User already exists"))
				.when(userRegistration).register(any(User.class), any(UserInfo.class));

		mockMvc.perform(post("/register")
				.param("username", "george")
				.param("password", "pass")
				.param("firstName", "George")
				.param("lastName", "Dimos")
				.param("email", "dimgeorge91@yahoo.gr")
				.with(csrf())
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/register"))
				.andExpect(flash().attribute("success", nullValue()))
				.andExpect(flash().attributeExists("userAlreadyExists"));
	}

	@Test
	void confirmRegistration() throws Exception {
		mockMvc.perform(get("/register/someIdentificationToken"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeExists("success"));
	}

}
