package com.mrkt.authorization.core;

import java.util.UUID;

import com.mrkt.authorization.model.Token;
import com.mrkt.usr.model.UserBase;

public class TokenIssues {
	
	private static String newSecret(){
		String secret = UUID.randomUUID().toString().replaceAll("-", "");
		return secret;
	}
	
	public static Token tokenGenerator(UserBase user){
		Token token = new Token(newSecret(), user.getuName(), user.getUid());
		return token;
	}
}
