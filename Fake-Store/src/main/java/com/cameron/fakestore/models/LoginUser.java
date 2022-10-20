package com.cameron.fakestore.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginUser {
    @Email(message="Please enter a valid email!")
    private String email;

    @Size(min=8, max=128, message="Password must be at least 8 characters")
    private String password;
    
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

    
    public LoginUser() {}
}
