package com.mrkt.product.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.mrkt.product.model.Product;
import com.mrkt.usr.dao.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testSaveAndFlush() {
		Product entity = new Product();
		entity.setName("测试新增商品3");
		entity.setMrktUser(userRepository.findOne(1l));
		assertNotNull(productRepository.saveAndFlush(entity));
	}
	
	@Test
	public void testFindOne() {
		Product entity = productRepository.findOne(1l);
		assertNotNull(entity);
		System.out.println(JSON.toJSONString(entity));
	}
	
	@Test
	public void testFindByIdAndState() {
		Product entity = productRepository.findByIdAndState(1l, 1);
		assertNotNull(entity);
		System.out.println(entity.toString());
	}
	
}
