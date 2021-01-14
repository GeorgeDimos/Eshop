package com.spring.eshop.controller;

import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.implementations.actions.ActionService;
import com.spring.eshop.service.implementations.actions.request.PasswordRecoveryEmail;
import com.spring.eshop.service.implementations.actions.request.ResendActivationEmail;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import com.spring.eshop.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecoveryController.class)
class RecoveryControllerTest {

	@MockBean
	IConfirmationTokenService confirmationTokenService;

	@MockBean
	ActionService actionService;

	@MockBean
	IUserService userService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void getActivationEmail() throws Exception {
		mockMvc.perform(get("/recover/activationEmail"))
				.andExpect(view().name("resendActivationEmail"));
	}

	@Test
	void resendActivationEmail() throws Exception {
		mockMvc.perform(post("/recover/activationEmail")
				.param("username", "geoge")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("success"))
				.andExpect(view().name("redirect:/login"));
	}

	@Test
	void resendActivationEmailInvalidUsername() throws Exception {
		doThrow(new InvalidUserInfoException("Invalid Username"))
				.when(actionService).request(any(ResendActivationEmail.class));

		mockMvc.perform(post("/recover/activationEmail")
				.param("username", "doesntExist")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("resendActivationEmail"));
	}

	@Test
	void getPasswordRecovery() throws Exception {
		mockMvc.perform(get("/recover/password"))
				.andExpect(view().name("recover-password"));
	}

	@Test
	void recoverPassword() throws Exception {
		mockMvc.perform(post("/recover/password")
				.param("username", "geoge")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("success"))
				.andExpect(view().name("redirect:/login"));
	}

	@Test
	void recoverPasswordInvalidUsername() throws Exception {
		doThrow(new InvalidUserInfoException("Invalid Username"))
				.when(actionService).request(any(PasswordRecoveryEmail.class));

		mockMvc.perform(post("/recover/password")
				.param("username", "doesntExist")
				.param("email", "dimgeorge91@yahoo.gr")
		)
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("recover-password"));
	}

	@Test
	void getPasswordInputPage() throws Exception {
		given(confirmationTokenService.isValid(any())).willReturn(true);
		mockMvc.perform(get("/recover/someInvalidToken"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("new-password"));
	}

	@Test
	void getPasswordInputPageInvalidToken() throws Exception {
		given(confirmationTokenService.isValid(any())).willReturn(false);
		mockMvc.perform(get("/recover/someInvalidToken"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/error"));
	}

	@Test
	void changePassword() throws Exception {
		mockMvc.perform(post("/recover/someToken")
				.param("password", "validPassword")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("success"))
				.andExpect(view().name("redirect:/login"));
	}

	@Test
	void changePasswordFieldErrors() throws Exception {
		mockMvc.perform(post("/recover/someToken")
				.param("password", "")
		)
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(view().name("new-password"));
	}
}