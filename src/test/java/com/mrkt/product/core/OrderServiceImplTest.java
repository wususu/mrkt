package com.mrkt.product.core;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.product.model.Order;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.core.UserServiceImpl;

/**
 * @ClassName	OrderServiceImplTest
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/26 15:54:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Before
	public void setUp() throws Exception {
		ThisUser.set(userServiceImpl.get(2l));// 买家
//		ThisUser.set(userServiceImpl.get(1l));// 卖家
	}
	
	@Test
	public void testRequestOrder() throws Exception {
		Order order = new Order();
		order.setMessage("留言");
		orderService.requestOrder(order, 1l);
	}

	@Test
	public void testProcessOrder() throws Exception {
		System.err.println(
//				orderService.processOrder("40286e8161d1280b0161d1281f5e0000", 2));// 卖家接受预定
				orderService.processOrder("40286e8161d1280b0161d1281f5e0000", 0));// 卖家拒绝预定
	}

	@Test
	public void testSubmitOrder() throws Exception {
		Order order = new Order();
		order.setId("40286e8161d1280b0161d1281f5e0000");
		order.setBuyerName("黄某某");
		order.setAddress("华农");
		order.setBuyerPhone("15555");
		order.setBuyerWx("asfasf");
		System.err.println(
				orderService.submitOrder(order));
	}

	@Test
	public void testEndOrder() throws Exception {
		System.err.println(
				orderService.endOrder("40286e8161d1280b0161d1281f5e0000"));
	}

	@Test
	public void testCommentSeller() throws Exception {
		System.err.println(
				orderService.commentSeller("40286e8161d1280b0161d1281f5e0000", 3, "卖家很有趣"));
	}

	@Test
	public void testCommentBuyer() throws Exception {
		System.err.println(
				orderService.commentBuyer("40286e8161d1280b0161d1281f5e0000", 3, "买家很会砍价"));
	}

	@Test
	public void testFindOne() throws Exception {
		System.err.println(
				orderService.findOne("40286e8161d1280b0161d1281f5e0000"));
	}

	@Test
	public void testFindByStateAsBuyer() throws Exception {
		List<Order> list = orderService.findByStateAsBuyer(0, 2);
		System.err.println(list.size());
		for (Order order : list) {
			System.err.println(order);
		}
	}

	@Test
	public void testFindByStateAsSeller() throws Exception {
		List<Order> list = orderService.findByStateAsSeller();
		System.err.println(list.size());
		for (Order order : list) {
			System.err.println(order);
		}
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		System.err.println(
				orderService.deleteOrder("40286e8161d162d70161d162eb880003"));
	}

}
