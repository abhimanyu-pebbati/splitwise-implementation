package com.spliwise.spliwiseapp.util;

public class StringFunctions {

	public static boolean isEmpty(String str) {
		if (str != null && str.trim().equals(""))
			return true;
		else
			return false;
	}

	public static boolean emailValidation(String email) {
		if (!email.contains("@"))
			return false;

		if (!email.contains(".com"))
			return false;

		if (email.indexOf("@") > email.indexOf(".com"))
			return false;
		
		return true;
	}

}
