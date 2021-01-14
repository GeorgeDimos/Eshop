package com.spring.eshop.controller;

import com.spring.eshop.exceptions.UserAlreadyExistsException;
import com.spring.eshop.service.implementations.actions.ActionService;
import com.spring.eshop.service.implementations.actions.request.UserRegistration;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

	@MockBean
	ActionService actionService;

	@MockBean
	IUserService userService;

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
	void registerNewUser() throws Exception {
		mockMvc.perform(post("/register")
				.param("username", "george")
				.param("password", "pass")
				.param("firstName", "George")
				.param("lastName", "Dimos")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("success"))
				.andExpect(view().name("redirect:/login"));

	}

	@Test
	void registerNewUserFieldError() throws Exception {
		mockMvc.perform(post("/register")
				.param("username", "george")
				.param("password", "")
				.param("firstName", "George")
				.param("lastName", "Dimos")
				.param("email", "dimgeorge91")
		)
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attributeHasFieldErrors("userInfo", "email"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));

	}

	@Test
	void registerNewUserFailureUserExists() throws Exception {
		doThrow(new UserAlreadyExistsException("User already exists"))
				.when(actionService).register(any(UserRegistration.class));

		mockMvc.perform(post("/register")
				.param("username", "george")
				.param("password", "pass")
				.param("firstName", "George")
				.param("lastName", "Dimos")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/register"))
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