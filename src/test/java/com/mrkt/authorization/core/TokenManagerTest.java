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
public class TokenManagerTest {

	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() {
		Token token =null;
		UserBase userBase = userRepository.findOne((long)1);
		token = tokenManager.create(userBase);
		Assert.assertNotEquals(token, tokenManager.create(userBase));
		System.out.println(token.getSrect() + "|" + token.getSrect().length());
		Assert.assertTrue(tokenManager.check(token.getSrect()));
		tokenManager.remove(token.getSrect());
		Assert.assertSame(null, tokenManager.get(token.getSrect()));
		Assert.assertFalse(tokenManager.check(token.getSrect()));
	}

}
