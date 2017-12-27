package com.mrkt.authorization.core;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.authorization.model.Token;
import com.mrkt.usr.dao.UserRepository;
import com.mrkt.usr.model.UserBase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenIssuesTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void test() {
		UserBase userBase = userRepository.findOne((long)1);
		Token token = TokenIssues.tokenGenerator(userBase);
		for(int i=0; i<999999; i++)
		{
			Assert.assertNotEquals(token, TokenIssues.tokenGenerator(userBase));
		}
	}

}
