package com.mrkt.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.product.core.IOrderService;
import com.mrkt.product.model.Order;

/**
 * 商品订单 控制器.
 * 
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 买家请求预定商品
	 * @param productId 商品id
	 * @param message 预定留言
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/product/{product_id}", method=RequestMethod.POST)
	public String requestOrder(
			@PathVariable("product_id") Long productId,
			@RequestParam("message") String message) {
		Order order = new Order();
		order.setMessage(message);
		return orderService.requestOrder(order, productId) ?
				"success" : "error";
	}
	
	/**
	 * 卖家接受预定
	 * @param id 订单id
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String acceptOrder(
			@PathVariable("id") String id) {
		return orderService.processOrder(id, 2) ?
				"success" : "error";
	}
	
	/**
	 * 卖家拒绝预定，或者关闭交易
	 * @param id 订单id
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller", method=RequestMethod.DELETE)
	public String cancelOrder(
			@PathVariable("id") String id) {
		return orderService.processOrder(id, 0) ?
				"success" : "error";
	}
	
	/**
	 * 买家删除订单
	 * @param id
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer", method=RequestMethod.DELETE)
	public String deleteOrder(
			@PathVariable("id") String id) {
		return "";
	}
	
	/**
	 * 买家预定成功后完善订单信息
	 * @param id 订单编号
	 * @param buyerName 收货人
	 * @param address 地址
	 * @param phone 联系方式
	 * @param buyerWx 微信（可选）
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String submitOrder(
			@PathVariable("id") String id,
			@RequestParam("buyer_name") String buyerName,
			@RequestParam("address") String address,
			@RequestParam("phone") String phone,
			@RequestParam("buyer_wx") String buyerWx) {
		Order order = new Order();
		order.setId(id);
		order.setBuyerName(buyerName);
		order.setAddress(address);
		order.setBuyerPhone(phone);
		order.setBuyerWx(buyerWx);
		return orderService.submitOrder(order) ?
				"success" : "error";
	}
	
	/**
	 * 买家确认收货，交易结束
	 * @param id
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/end", method=RequestMethod.PUT)
	public String endOrder(
			@PathVariable("id") String id) {
		return orderService.endOrder(id) ?
				"success" : "error";
	}
	
	/**
	 * 交易结束后，卖家评价买家
	 * @param id 订单id
	 * @param score 分数1-5
	 * @param comment 评语
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/comment", method=RequestMethod.PUT)
	public String commentSeller(
			@PathVariable("id") String id,
			@RequestParam("score") Integer score,
			@RequestParam("comment") String comment) {
		return orderService.commentBuyer(id, score, comment) ?
				"success" : "error";
	}
	
	/**
	 * 买家评价卖家
	 * @param id 订单id
	 * @param score 分数1-5
	 * @param comment 评语
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller/comment", method=RequestMethod.PUT)
	public String commentBuyer(
			@PathVariable("id") String id,
			@RequestParam("score") Integer score,
			@RequestParam("comment") String comment) {
		return orderService.commentSeller(id, score, comment) ?
				"success" : "error";
	}
	
	/**
	 * 查看订单详情
	 * @param id 订单编号
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String findOne(
			@PathVariable("id") String id) {
		return JSON.toJSONString(orderService.findOne(id));
	}
	
	/**
	 * 查询我预定的，预定中的订单，被取消/拒绝的订单，预订成功待完善信息的订单
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/ordering", method=RequestMethod.GET)
	public String findOrdering() {
		return JSON.toJSONString(
				orderService.findByStateAsBuyer(0, 2));
	}
	
	/**
	 * 查询我买到的，包括待收货和待评价和评价好的
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/buy", method=RequestMethod.GET)
	public String findBuy() {
		return JSON.toJSONString(
				orderService.findByStateAsBuyer(3, 4));
	}
	
	/**
	 * 查询我卖出的
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller", method=RequestMethod.GET)
	public String findByStateAsSeller() {
		return JSON.toJSONString(
				orderService.findByStateAsSeller());
	}
}
