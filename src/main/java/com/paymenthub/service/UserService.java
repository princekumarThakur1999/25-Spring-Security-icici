package com.paymenthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymenthub.dto.UserLogin;
import com.paymenthub.dto.UserRegister;
import com.paymenthub.entity.UserEntity;
import com.paymenthub.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	public String userResgister(UserRegister userregister) {
		
		//convert userregister to entity object
		UserEntity enity = new UserEntity();
		enity.setEmail(userregister.getEmail());
		enity.setPassword(userregister.getPassword());
		enity.setName(userregister.getName());
		enity.setContact(userregister.getContact());
		
		//db activity perform on entity object
		repository.save(enity);
		
		return "Successfully Register you data. Please Try to login";
	}

	public String userLogin(UserLogin login) {
		
		//perform db opertation
		//using
		UserEntity entity = repository.findByEmailAndPassword(login.getEmail(), login.getPassword());
		
		if(entity != null) {
			return "Welcome user to Icici Profile";
		}
		else {
		return "Please try to login, Once";
		}
	}
	
	
}
