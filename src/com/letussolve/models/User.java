package com.letussolve.models;

import java.util.Date;

import com.letussolve.utils.PasswordHasher;

public class User {
	private int userID;
	private String firstName; 
	private String lastName;
	private String email;
	private String mobile;
	private String username;
	private String password;
	private boolean isActive;
	private Date createdAt;
	private Date updatedAt;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int i) {
		this.userID = i;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPassword(String password, boolean flag) {
		if (flag) {
			PasswordHasher hasher = new PasswordHasher();
			this.password = hasher.createHash(password);
		}
		this.password = password;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
