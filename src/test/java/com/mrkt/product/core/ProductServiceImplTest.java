package com.mrkt.product.core;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.core.UserServiceImpl;

/**
 * @ClassName	ProductServiceImplTest	
 * @Description
 * @author		hdonghong
 * @version 	v1.0 
 * @since		2018/02/19 11:37:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	private IProductService productService;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Before
	public void setUp() throws Exception {
		ThisUser.set(userServiceImpl.get(1l));// 卖家
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindOne() throws Exception {
		Product product = productService.findOne(1l);
		assertNotNull(product);
		System.out.println(product);
		/*
		 * Product [id=1, name=, desc=, price=0.0, views=13, collection=null, likes=-1, ptype=,, 
		 * traWay=, count=null, state=null, tmCreated=2018-02-14 21:25:20.0, tmUpdated=2018-02-21 21:26:04.0, 
		 * mrktUser=1#oReRW1LlHXe-MZxueuBgjs53ghRYChandler, 
		 * images=[com.mrkt.product.model.Image@8bde368, com.mrkt.product.model.Image@34e68840], isLike=false]
		 * 
		 * Product [id=1, name=, desc=, price=0.0, views=14, collection=null, likes=-1, ptype=, 
		 * traWay=, count=null, state=null, tmCreated=2018-02-14 21:25:20.0, tmUpdated=2018-02-21 21:26:04.0, 
		 * mrktUser=1#oReRW1LlHXe-MZxueuBgjs53ghRYChandler, 
		 * images=[com.mrkt.product.model.Image@3d7caf9c, com.mrkt.product.model.Image@6e617c0e], isLike=true]
		 */
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		for (int i = 1; i < 10; i++) {
		Product product = new Product();
			product.setMrktUser(ThisUser.get());
			product.setName("业务层：新增商品" + i);
			product.setDesc("业务层测试数据");
			productService.saveOrUpdate(product);
		}
		
	}

	@Test
	public void testFindPage() throws Exception {
		Page<Product> page = productService.findPage(0, 1L, "views", "商品");
//		Page<Product> page = productService.findPage(0, "衣服鞋子", "tmCreated", "商品");
		System.out.println(page.getSize() + ": " + page.getTotalElements());
		for (Product product : page) {
			System.out.println(product);
		}
	}

	@Test
	public void testCancel() throws Exception {
		productService.cancel(1l);
	}

	@Test
	public void testCancelAll() throws Exception {
		productService.cancelAll(new Long[] {1l});
	}

	@Test
	public void testDelete() throws Exception {
		productService.delete(1l);
	}

	@Test
	public void testDeleteAll() throws Exception {
		productService.deleteAll(new Long[] {1l});
	}
	
	@Test
	public void testAddLikes() throws Exception {
		productService.addLikes(1l);
	}
	
	@Test
	public void testRemoveLikes() throws Exception {
		productService.removeLikes(1l);
	}
	
	@Test
	public void testGetMine() throws Exception {
		List<Product> list = productService.getMine();
		list.forEach(System.out::println);
	}
	
}
