package com.paymenthub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymenthub.dto.UserRegister;
import com.paymenthub.entity.UserEntity;
import com.paymenthub.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public String userResgister(UserRegister userregister) {
		
		//convert userregister to entity object
		UserEntity enity = new UserEntity();
		enity.setEmail(userregister.getEmail());
		enity.setPassword(bCryptPasswordEncoder.encode(userregister.getPassword()));
		enity.setName(userregister.getName());
		enity.setContact(userregister.getContact());
		
		//db activity perform on entity object
		repository.save(enity);
		
		return "Successfully Register you data. Please Try to login";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//this method will directily interract with repository layer
		
		Optional<UserEntity> user =  repository.findById(username);
		UserEntity userdetails = user.orElseThrow(()-> new RuntimeException("Email id is not found."));
			
		return userdetails;
	}

	/* This is old way to logged in, but we have to us security layer to login.
	 * public String userLogin(UserLogin login) {
		
		//perform db opertation
		//using
		UserEntity entity = repository.findByEmailAndPassword(login.getEmail(), login.getPassword());
		
		if(entity != null) {
			return "Welcome user to Icici Profile";
		}
		else {
		return "Please try to login, Once";
		}
	}*/
	
	
	
}
