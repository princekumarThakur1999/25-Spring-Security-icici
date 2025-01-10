package com.paymenthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymenthub.dto.UserLogin;
import com.paymenthub.dto.UserLoginResponse;
import com.paymenthub.dto.UserRegister;
import com.paymenthub.service.JWTTokenUtil;
import com.paymenthub.service.UserService;

@RestController
@RequestMapping("/icici")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	JWTTokenUtil jwtTokenutil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@GetMapping("/info")
	public String welcomePayhub() {
		
		return "Welcome to Payhub center";
	}
	
	@PostMapping("/register")
	public String userRegistration(@RequestBody UserRegister userregister) {
		
		String result = service.userResgister(userregister);
	
		return result;
	}
	
	//TODO :: Need to Add Security in the login + JWT token
	
	@PostMapping("/login/user")
	public UserLoginResponse userLogin(@RequestBody UserLogin login) {
		// TODO : we need to pass Credentials to the Spring Security layer
		doAuthentication(login.getEmail(), login.getPassword());
		
			
		String token = this.jwtTokenutil.createJWTToken(login.getEmail()); //if login is successfull then create a token with username
		
		UserLoginResponse response = new UserLoginResponse();
		response.setEmail(login.getEmail());
		response.setToken(token);
		
		//Internally,Spring security layer validate User credentials
		//internally spring security layer is connected to DB, and check the username & password is present or not
		//if user is valid and certified by spring security, generate token and attached as
		//part of response object
		
		//jwt token Generation : by passing login username
		//Incase username is invalid, then we will sent like invalid credentials
		
		
		
	//	String result = service.userLogin(login);
	
		return response;
	}
	
	private void doAuthentication(String username, String password) {
		
		//passing username and pasword to Authentication Manager
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch(BadCredentialsException e) {
			throw new RuntimeException("Invalid username and Password ");
		}
	}
	
	@GetMapping("/get/token")
	public String getToken() {
		
		return jwtTokenutil.createJWTToken("Princekumar@alacriti.com");
	}
	
	@GetMapping("/validate/token")
	public boolean validToken(@RequestHeader String token) {
		
		return jwtTokenutil.isValidToken("Princekumar@alacriti.com", token);
	}
}
