package com.spring.eshop.security;

import com.spring.eshop.service.interfaces.IUserService;
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

	private final IUserService userDetailsService;

	@Autowired
	public WebSecurityConfig(IUserService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

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
				.and()
				.formLogin().loginPage("/login")
				.defaultSuccessUrl("/successful-login", true)
				.and()
				.logout().permitAll()
				.and().exceptionHandling().accessDeniedPage("/access-denied");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		provider.setAuthoritiesMapper(authoritiesMapper());
		provider.setPostAuthenticationChecks(UserDetails::isEnabled);
		auth.authenticationProvider(provider);
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
