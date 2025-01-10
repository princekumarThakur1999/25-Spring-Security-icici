package com.paymenthub.security;

import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	JWTTokenAuthenticationFilter jwttokenAuthFilter;
	
	//Few Predefined Bean I have to Define
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
	//password always should have in encrypted/encoded
	BCryptPasswordEncoder getBpCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

		security.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers("/register", "/login/user").permitAll()
			.anyRequest().authenticated()
			.and().addFilterBefore(this.jwttokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
	    return security.build();
	}
}
