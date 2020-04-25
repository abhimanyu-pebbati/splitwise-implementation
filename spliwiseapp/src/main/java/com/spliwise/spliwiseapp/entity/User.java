package com.spliwise.spliwiseapp.entity;

import lombok.Getter;

@Getter
public class User {
	private String userId;
	private String fullName;
	private String email;

	public User(String fullName, String email) {
		this.userId = fullName.toLowerCase();
		this.fullName = fullName;
		this.email = email;
	}

}
