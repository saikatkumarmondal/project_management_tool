package com.pmt.payload;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank(message = "Username can not be blank")
	private String username;
	@NotBlank(message = "Password can not be balnk")
	private String password;
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
	public LoginRequest(String username,String password) {
		
		this.username = username;
		this.password = password;
	}
	
	
}
