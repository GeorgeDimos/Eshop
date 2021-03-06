package com.spring.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = {"/home", "/"})
	public String home() {
		return "home";
	}

	@GetMapping(value = "/access-denied")
	public String accessDenied() {
		return "accessDenied";
	}

	@GetMapping("/error")
	public String getError() {
		return "error";
	}
}
