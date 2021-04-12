package com.spring.eshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

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
						.antMatchers("/checkout/**").authenticated()
						.antMatchers("/register/**", "/login/**", "/recover/**").not().authenticated()
						.antMatchers("/user/**").hasAnyAuthority("admin", "user")
						.antMatchers("/api/**").hasAuthority("admin")
						.antMatchers("/resources/**").permitAll()
						.and().oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfo -> userInfo
										.userAuthoritiesMapper(this.oauth2UserAuthoritiesMapper())
						)
						.loginPage("/login")
		)
						.httpBasic()
						.and()
						.formLogin().loginPage("/login").defaultSuccessUrl("/successful-login", true)
						.and()
						.logout().permitAll()
						.and().exceptionHandling().accessDeniedPage("/access-denied");
	}

	private GrantedAuthoritiesMapper oauth2UserAuthoritiesMapper() {
		return (authorities) -> Set.of((GrantedAuthority) () -> "oauth2");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
}
