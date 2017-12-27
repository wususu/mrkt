package com.mrkt.sys.config;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfiguratorTest {

	private Configurator configurator = Configurator.getInstance();
	
	@Test
	public void test() {
		Assert.assertEquals("X-mrkt-Authentication-Type", configurator.get("com.mrkt.request.auth_type"));
		Assert.assertEquals("X-mrkt-Authentication-Token", configurator.get("com.mrkt.request.auth_header"));
		Assert.assertEquals("Token", configurator.get("com.mrkt.request.auth_type.token"));
		Assert.assertNotEquals("Token", configurator.get("com.mrkt.request.auth_type.token11"));
	}

}
