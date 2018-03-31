package com.mrkt.product.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.product.model.Order;

/**
 * @ClassName	OrderRepositoryTest
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/26 10:02:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByBuyerId() {
		Order order = orderRepository.findByIdAndBuyerId("133", 1l);
		System.out.println(order);
	}

}
