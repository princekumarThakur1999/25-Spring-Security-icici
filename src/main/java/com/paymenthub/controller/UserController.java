package com.paymenthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymenthub.dto.UserLogin;
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
	public String userLogin(@RequestBody UserLogin login) {
		
		String result = service.userLogin(login);
	
		return result;
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
