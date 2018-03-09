package com.mrkt.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.model.ReturnModel;
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
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/product/{product_id}", method=RequestMethod.POST)
	public ReturnModel requestOrder(
			@PathVariable("product_id") Long productId,
			@RequestParam("message") String message) throws Exception {
		Order order = new Order();
		order.setMessage(message);
		return orderService.requestOrder(order, productId) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 卖家接受预定
	 * @param id 订单id
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ReturnModel acceptOrder(
			@PathVariable("id") String id) throws Exception {
		return orderService.processOrder(id, 2) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 卖家拒绝预定，或者关闭交易
	 * @param id 订单id
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller", method=RequestMethod.DELETE)
	public ReturnModel cancelOrder(
			@PathVariable("id") String id) throws Exception {
		return orderService.processOrder(id, 0) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 买家删除订单
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer", method=RequestMethod.DELETE)
	public ReturnModel deleteOrder(
			@PathVariable("id") String id) throws Exception {
		return orderService.deleteOrder(id) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 买家预定成功后完善订单信息
	 * @param id 订单编号
	 * @param buyerName 收货人
	 * @param address 地址
	 * @param phone 联系方式
	 * @param buyerWx 微信（可选）
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ReturnModel submitOrder(
			@PathVariable("id") String id,
			@RequestParam("buyer_name") String buyerName,
			@RequestParam("address") String address,
			@RequestParam("phone") String phone,
			@RequestParam("buyer_wx") String buyerWx) throws Exception {
		Order order = new Order();
		order.setId(id);
		order.setBuyerName(buyerName);
		order.setAddress(address);
		order.setBuyerPhone(phone);
		order.setBuyerWx(buyerWx);
		return orderService.submitOrder(order) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 买家确认收货，交易结束
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/end", method=RequestMethod.PUT)
	public ReturnModel endOrder(
			@PathVariable("id") String id) throws Exception {
		return orderService.endOrder(id) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 交易结束后，卖家评价买家
	 * @param id 订单id
	 * @param score 分数1-5
	 * @param comment 评语
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/comment", method=RequestMethod.PUT)
	public ReturnModel commentSeller(
			@PathVariable("id") String id,
			@RequestParam("score") Integer score,
			@RequestParam("comment") String comment) throws Exception {
		return orderService.commentBuyer(id, score, comment) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 买家评价卖家
	 * @param id 订单id
	 * @param score 分数1-5
	 * @param comment 评语
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller/comment", method=RequestMethod.PUT)
	public ReturnModel commentBuyer(
			@PathVariable("id") String id,
			@RequestParam("score") Integer score,
			@RequestParam("comment") String comment) throws Exception {
		return orderService.commentSeller(id, score, comment) ?
				ReturnModel.SUCCESS() : ReturnModel.ERROR();
	}
	
	/**
	 * 查看订单详情
	 * @param id 订单编号
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ReturnModel findOne(
			@PathVariable("id") String id) throws Exception {
		return ReturnModel.SUCCESS(orderService.findOne(id));
	}
	
	/**
	 * 查询我预定的，预定中的订单，被取消/拒绝的订单，预订成功待完善信息的订单
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/ordering", method=RequestMethod.GET)
	public ReturnModel findOrdering() throws Exception {
		return ReturnModel.SUCCESS(orderService.findByStateAsBuyer(0, 2));
	}
	
	/**
	 * 查询我买到的，包括待收货和待评价和评价好的
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/buyer/buy", method=RequestMethod.GET)
	public ReturnModel findBuy() throws Exception {
		return ReturnModel.SUCCESS(orderService.findByStateAsBuyer(3, 4));
	}
	
	/**
	 * 查询我卖出的
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/{id}/seller", method=RequestMethod.GET)
	public ReturnModel findByStateAsSeller() throws Exception {
		return ReturnModel.SUCCESS(orderService.findByStateAsSeller());
	}
}
