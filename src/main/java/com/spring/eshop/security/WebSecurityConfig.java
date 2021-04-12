package com.spring.eshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
						.csrf().ignoringAntMatchers("/api/**")
						.and()
						.authorizeRequests()
						.antMatchers("/checkout/**", "/user/**").authenticated()
						.antMatchers("/register/**", "/login/**", "/recover/**").not().authenticated()
						//.antMatchers("/api/**").hasRole("ADMIN")
						.antMatchers("/resources/**").permitAll()
						//.and().oauth2Login().loginPage("/login").defaultSuccessUrl("/test")
						.and().formLogin().loginPage("/login").defaultSuccessUrl("/successful-login", true)
						.and()
						.logout().permitAll()
						.and().exceptionHandling().accessDeniedPage("/access-denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
