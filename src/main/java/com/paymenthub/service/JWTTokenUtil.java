package com.paymenthub.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenUtil {

	//Token creation - JWT API
	
	//1. Subject creation - Username
	//2. At what time token is created
	//3. At what time it will be expire
	//4. Choose a Algorithum to create a token  
	//5. create a token with the help to token method.
	private long expMillsecond = 360000;
	private final String SECRET_KEY = "jsajsfucjas+ajs+32casdfjhsdbfjahcacamli++asgnmoomoncaeiqw9w8ry39acmaasdfce2342wgvdsddskfnascaksdkkknaklamsdmkac";
	
	//Creating a token for username
	public String createJWTToken(String username) {
		
		@SuppressWarnings("deprecation")
		String token = Jwts.builder().setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expMillsecond ))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
		System.out.println(System.currentTimeMillis());
		
		return token;
	}
	
	//Validation of Token
	public boolean isValidToken(String username, String token) {
		
		//Step 1 : Extract username form the token
		String tokenUsername = getUsernameFromToken(token);
		
		boolean isExpired = isTokenExpired(token);
		System.out.println("IS token is expired : " + isExpired);   //if my token is not expired it will return false
		
		//Step 2 : Compare the username from the token extracted username
		System.out.println("Is username is same in the token : " + username.equals(tokenUsername));
		
		return username.equals(tokenUsername) && (!isExpired);  //if both are true then only token is valid --> i'm converting isExpired as true to get valid token response
	}
	
	//getUsername form the token
	public String getUsernameFromToken(String token) {
		
		String tokenUsername = Jwts.parser()                      //get token with help of parser method
								.setSigningKey(SECRET_KEY)        // to decript the JWT token we have to user SECRET_KEY
								.parseClaimsJws(token)            // get Payload from JWT token ( also called as claims) 
								.getBody()                        // get body of the payload
								.getSubject();					 // get subject form the body of the payload
		
		return tokenUsername;
	}
	
	//check token is expired or not
	public boolean isTokenExpired(String token) {
		
		//get expiry date from the Token payload
		Date expiredTime = Jwts.parser()                      //get token with help of parser method
								.setSigningKey(SECRET_KEY)        // to decript the JWT token we have to user SECRET_KEY
								.parseClaimsJws(token)            // get Payload from JWT token ( also called as claims) 
								.getBody()                        // get body of the payload
								.getExpiration();					 // get expiry time form the body of the payload
		
		System.out.println("Expire time is : " + expiredTime);
		return expiredTime.before(new Date());  // expired time should be greater then current time 
	}
}
