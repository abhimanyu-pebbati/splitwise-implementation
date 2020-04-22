package com.spliwise.spliwiseapp.util;

import java.util.UUID;

public class UniqueIdFunction {
	
	public static String generateUniqueId()
	{
		UUID uuid = UUID.randomUUID();

		return uuid.toString();
	}

}
