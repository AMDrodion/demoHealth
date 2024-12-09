package com.health.demo.security;

import com.health.demo.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
						.authorizeHttpRequests(auth -> auth
										//.requestMatchers("/people", "/wards", "/diagnoses").authenticated()
										.requestMatchers("/people/new", "/people/edit/**", "/people/delete/**",
														"/wards/new", "wards/edit/**", "wards/delete/**",
														"/diagnoses/new", "diagnoses/edit/**", "diagnoses/delete/**").hasRole("ADMIN")
										.requestMatchers("/", "/login").permitAll()
										.anyRequest().authenticated()
						)
						.formLogin(form -> form
										.defaultSuccessUrl("/people", true)
										.failureUrl("/login?error=true")
										.permitAll()
						)
						.logout(logout -> logout
										.logoutUrl("/logout")
										.logoutSuccessUrl("/login?logout")
										.invalidateHttpSession(true)
										.deleteCookies("JSESSIONID")
						)
						.userDetailsService(userDetailsService);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
