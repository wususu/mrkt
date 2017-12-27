package com.mrkt.usr.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.usr.model.Customer;
import com.mrkt.usr.model.UserBase;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void test() {
		UserBase userBase = null;
		assertNotNull(userBase = userRepository.findOne((long)1));
		assertSame(Customer.class, userBase.getClass());
	}

}
