package com.mrkt.authorization.core;

import org.springframework.stereotype.Service;

import com.mrkt.authorization.model.Token;
import com.mrkt.usr.model.UserBase;

@Service
public interface TokenManager {

	Token create(UserBase userBase);
		
	Token get(String srect);
	
	void remove(String srect);

	Boolean check(String srect);
	
}
