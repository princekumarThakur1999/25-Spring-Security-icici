package com.paymenthub.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paymenthub.service.JWTTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//verify the token is present on Request header or not
		//if not present any token then, we will send Token required in the request
		
		//if token is present then  get username from the token
		//if username is valid then validate the token w.r.to expiry and user from HTTPRequest
		
	    //if token is valid then 
					//Security layer will check the username is present in DB or not.
			
		//if username is present in the DB the request is allowed to hit to controller layer
		
		String token =  request.getHeader("Authorization"); //In Header Authorization key will hold token value
		System.out.println("Token is : " + token);
		
		String userNamefromToken = null;
		
		if( token == null) {
			System.out.println("Please add token... Token is Missing ... Token isnot present in the Header");
		}
		else {
			userNamefromToken = jwtTokenUtil.getUsernameFromToken(token);
		}
		
		if(userNamefromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//Security layer will verify the user is present in the DB or not.
			
			//if present then allow request to hit controller layer
			
		}
	}

}
