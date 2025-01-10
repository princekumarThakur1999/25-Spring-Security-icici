package com.paymenthub.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paymenthub.service.JWTTokenUtil;
import com.paymenthub.service.UserService;

@Component
public class JWTTokenAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	UserService userservice;

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
			
			//DB check
			//Security layer will verify the user is present in the DB or not.
			UserDetails userDetails = this.userservice.loadUserByUsername(userNamefromToken);  //UserDetails is implemented by UserEntity
			
			//Validate JWT token and expiry time
			boolean isValidToken =  this.jwtTokenUtil.isValidToken(userDetails.getUsername(), token);
			
			if(isValidToken) {
				
				//Update Secuirty context data for that user
				
				//Spring Security layer provide  a token class, and a part of token class we are going to pass User Information
				UsernamePasswordAuthenticationToken usernamepasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamepasswordAuthToken);
				
			}else {
				System.out.println("Token is invalid ...Please come with Valid Token");
			}
			
			//if present then allow request to hit controller layer
		}
		
		//next filter Forwarding
		filterChain.doFilter(request, response);
	}
}
