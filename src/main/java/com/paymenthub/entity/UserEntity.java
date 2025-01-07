package com.paymenthub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "icici_userDetails")
public class UserEntity {
	
	@Id
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@Column
	private String contact;
	
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

}
