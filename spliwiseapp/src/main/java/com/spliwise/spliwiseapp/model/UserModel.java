package com.spliwise.spliwiseapp.model;

import java.util.HashMap;
import java.util.Map;

import com.spliwise.spliwiseapp.entity.User;

public class UserModel {

	private static UserModel instance;

	private Map<String, User> userTable;

	private UserModel() {
		userTable = new HashMap<String, User>();
	}

	public static UserModel getInstance() {
		if (instance == null)
			instance = new UserModel();

		return instance;
	}

	public void persistUser(User user) {
		this.userTable.put(user.getUserId(), user);
	}

	public User getUserById(String userId) {
		return this.userTable.get(userId);
	}
}
