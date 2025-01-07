package com.paymenthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymenthub.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	UserEntity findByEmailAndPassword(String email, String password);
	
	
}
