package com.mrkt.usr;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mrkt.usr.core.UserServiceImpl;
import com.mrkt.usr.model.Admin;
import com.mrkt.usr.model.Customer;
import com.mrkt.usr.model.UnLgnUser;
import com.mrkt.usr.model.UserBase;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThisUserTest {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Test
	public void test() {
		
		UserBase userBase = userServiceImpl.get((long)1);
		System.out.println("com.mrkt.usr.ThisUserTest.test(): " + userBase.toString());// TODO
		Assert.assertEquals(Customer.class, userBase.getClass());
		ThisUser.set(userBase);
		UserBase userBase2 = ThisUser.get();
		Assert.assertEquals(userBase, userBase2);
		ThisUser.remove();
		Assert.assertEquals(UnLgnUser.getInstance(), ThisUser.get());
		Assert.assertNotEquals(Admin.class, userBase.getClass());
	}

}
