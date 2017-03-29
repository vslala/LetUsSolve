package com.letussolve.models;

import java.util.Date;

public class Admin {
	private int adminId;
	private String name;
	private String username;
	private String password;
	private Date createdAt;
	private Date updatedAt;
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NAME: " + name)
			.append("USERNAME: " + username)
			.append("ID: " + adminId);
		return sb.toString();
	}
	
}
