package com.spring.eshop.controller;

import com.spring.eshop.exceptions.InvalidUserInfoException;
import com.spring.eshop.service.interfaces.IConfirmationTokenService;
import com.spring.eshop.service.interfaces.IRecoveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecoveryController.class)
class RecoveryControllerTest {

	@MockBean
	IConfirmationTokenService confirmationTokenService;

	@MockBean
	IRecoveryService recoveryService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void getActivationEmail() throws Exception {
		mockMvc.perform(get("/recover/activationEmail"))
						.andExpect(status().isOk())
						.andExpect(view().name("resendActivationEmail"));
	}

	@Test
	@WithMockUser
	void getActivationEmailLoggedIn() throws Exception {
		mockMvc.perform(get("/recover/activationEmail"))
						.andExpect(status().is4xxClientError());
	}

	@Test
	void resendActivationEmail() throws Exception {
		mockMvc.perform(post("/recover/activationEmail")
						.param("username", "geoge")
						.param("email", "dimgeorge91@yahoo.gr")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(flash().attributeExists("success"))
						.andExpect(view().name("redirect:/login"));
	}

	@Test
	void resendActivationEmailInvalidUsername() throws Exception {
		doThrow(new InvalidUserInfoException("Invalid Username"))
						.when(recoveryService).resendActivationEmail(anyString(), anyString());

		mockMvc.perform(post("/recover/activationEmail")
						.param("username", "doesntExist")
						.param("email", "dimgeorge91@yahoo.gr")
						.with(csrf())
		)
						.andExpect(status().isOk())
						.andExpect(model().attributeExists("error"))
						.andExpect(view().name("resendActivationEmail"));
	}

	@Test
	void getPasswordRecovery() throws Exception {
		mockMvc.perform(get("/recover/password"))
						.andExpect(status().isOk())
						.andExpect(view().name("recover-password"));
	}

	@Test
	void recoverPassword() throws Exception {
		mockMvc.perform(post("/recover/password")
						.param("username", "geoge")
						.param("email", "dimgeorge91@yahoo.gr")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(flash().attributeExists("success"))
						.andExpect(view().name("redirect:/login"));
	}

	@Test
	void recoverPasswordInvalidUsername() throws Exception {
		doThrow(new InvalidUserInfoException("Invalid Username"))
						.when(recoveryService).passwordRecoveryEmail(anyString(), anyString());

		mockMvc.perform(post("/recover/password")
						.param("username", "doesntExist")
						.param("email", "dimgeorge91@yahoo.gr")
						.with(csrf())
		)
						.andExpect(status().isOk())
						.andExpect(model().attributeExists("error"))
						.andExpect(view().name("recover-password"));
	}

	@Test
	void getPasswordInputPage() throws Exception {
		given(confirmationTokenService.isValid(anyString())).willReturn(true);
		mockMvc.perform(get("/recover/someInvalidToken"))
						.andExpect(status().isOk())
						.andExpect(model().attributeExists("user"))
						.andExpect(view().name("new-password"));
	}

	@Test
	void getPasswordInputPageInvalidToken() throws Exception {
		given(confirmationTokenService.isValid(anyString())).willReturn(false);
		mockMvc.perform(get("/recover/someInvalidToken"))
						.andExpect(status().is3xxRedirection())
						.andExpect(model().attributeDoesNotExist("user"))
						.andExpect(view().name("redirect:/error"));
	}

	@Test
	void changePassword() throws Exception {
		mockMvc.perform(post("/recover/someToken")
						.param("password", "validPassword")
						.with(csrf())
		)
						.andExpect(status().is3xxRedirection())
						.andExpect(flash().attributeExists("success"))
						.andExpect(view().name("redirect:/login"));
	}

	@Test
	void changePasswordFieldErrors() throws Exception {
		mockMvc.perform(post("/recover/someToken")
						.param("password", "")
						.with(csrf())
		)
						.andExpect(status().isOk())
						.andExpect(model().attributeHasFieldErrors("user", "password"))
						.andExpect(flash().attribute("success", nullValue()))
						.andExpect(view().name("new-password"));

		verify(recoveryService, never()).passwordRecoveryConfirmation(anyString(), anyString());
	}
}
