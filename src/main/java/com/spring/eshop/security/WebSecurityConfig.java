package com.spring.eshop.security;

import com.spring.eshop.service.implementations.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	final UserDetailsServiceImpl userDetailsService;

	@Autowired
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/checkout").authenticated()
				.antMatchers("/users/{userId}/**").access("@webSecurity.checkUserIdOrRole(authentication,#userId)")
				.and()
				.formLogin().loginPage("/login")
				.and()
				.logout().permitAll()
				.and().exceptionHandling().accessDeniedPage("/access-denied");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		provider.setAuthoritiesMapper(authoritiesMapper());
		provider.setPostAuthenticationChecks(UserDetails::isEnabled);
		return provider;
	}

	@Bean
	public GrantedAuthoritiesMapper authoritiesMapper() {
		SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
		authorityMapper.setConvertToUpperCase(true);
		authorityMapper.setDefaultAuthority("USER");
		return authorityMapper;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
