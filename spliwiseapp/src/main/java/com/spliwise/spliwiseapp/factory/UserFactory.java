package com.spliwise.spliwiseapp.factory;

import org.springframework.stereotype.Component;

import com.spliwise.spliwiseapp.entity.User;
import com.spliwise.spliwiseapp.model.UserModel;
import com.spliwise.spliwiseapp.util.StringFunctions;

@Component
public class UserFactory {
	private static UserFactory instance = null;
	private UserModel userModel;

	private UserFactory() {
		userModel = UserModel.getInstance();
	}

	public UserFactory getInstance() {
		if (instance == null)
			instance = new UserFactory();

		return instance;
	}

	public User createUser(String fullName, String email) {
		if (!this.dataValidation(fullName, email))
			return null;

		User user = new User(fullName.toLowerCase(), fullName, email);

		userModel.persistUser(user);

		return user;
	}

	public boolean dataValidation(String fullName, String email) {
		if (StringFunctions.isEmpty(fullName)) {
			System.out.println("Full name cannot be empty.");
			return false;
		}

		if (StringFunctions.isEmpty(email)) {
			System.out.println("E-mail cannot be empty.");
			return false;
		}

		if (!StringFunctions.emailValidation(email)) {
			System.out.println("Enter a valid email ID");
			return false;
		}

		return true;

	}
}
