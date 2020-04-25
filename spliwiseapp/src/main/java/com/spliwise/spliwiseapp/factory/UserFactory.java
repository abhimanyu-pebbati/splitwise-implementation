package com.spliwise.spliwiseapp.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spliwise.spliwiseapp.entity.User;
import com.spliwise.spliwiseapp.exceptionhandling.exception.InvalidUserException;
import com.spliwise.spliwiseapp.model.UserModel;
import com.spliwise.spliwiseapp.util.StringFunctions;

@Component
public class UserFactory {
	private static UserFactory instance = null;
	private static Logger logger = LoggerFactory.getLogger(UserFactory.class);
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

		User user = new User(fullName, email);
		if (userModel.getUserById(user.getUserId()) != null) {
			String err = "User with ID '" + user.getUserId() + "' already exists. Use another name.";
			logger.error(err);
			throw new InvalidUserException(err);
		}

		userModel.persistUser(user);

		return user;
	}

	public boolean dataValidation(String fullName, String email) {
		if (StringFunctions.isEmpty(fullName)) {
			String err = "Full name cannot be empty.";
			logger.error(err);
			throw new InvalidUserException(err);
		}

		if (StringFunctions.isEmpty(email)) {
			String err = "E-mail cannot be empty.";
			logger.error(err);
			throw new InvalidUserException(err);
		}

		if (!StringFunctions.emailValidation(email)) {
			String err = "Enter a valid email ID";
			logger.error(err);
			throw new InvalidUserException(err);
		}

		return true;

	}
}
