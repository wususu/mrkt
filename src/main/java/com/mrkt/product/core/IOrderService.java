package com.mrkt.product.core;

import java.util.List;

import com.mrkt.product.model.Order;

/**
 * @ClassName	IOrdeService
 * @Description 商品订单的服务接口
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/25 06:27:10
 */
public interface IOrderService {

	/**
	 * 当前用户请求预定商品，生成订单，等待卖家处理订单，order.state->1订单状态为请求预定，要求product.state->2
	 * @param order
	 * @param productId
	 * @return 买家和卖家同一个人则返回false
	 * @throws Exception 
	 */
	boolean requestOrder(Order order, Long productId) throws Exception;
	
	/**
	 * 商家处理订单，接受/拒绝/取消，order.state->2/0，订单状态变为待完善/取消订单product.state->1
	 * @param id 订单编号
	 * @param state
	 * @throws Exception 
	 */
	boolean processOrder(String id, int state) throws Exception;
	
	/**
	 * 卖家接受预定后买家填写订单信息，订单状态变为待确定收货，order.state->3
	 * @param order
	 * @throws Exception 
	 */
	boolean submitOrder(Order order) throws Exception;
	
	/**
	 * 买家确定收货，订单完成，order.state->4，订单状态变为完成，product.state->3
	 * @param id
	 * @throws Exception 
	 */
	boolean endOrder(String id) throws Exception;
	
	/**
	 * 买家评价卖家，只有已完成的交易才可被评价
	 * @param id
	 * @throws Exception 
	 */
	boolean commentSeller(String id, int score, String comment) throws Exception;
	
	/**
	 * 卖家评价买家，只有已完成的交易才可被评价
	 * @param id
	 * @throws Exception 
	 */
	boolean commentBuyer(String id, int score, String comment) throws Exception;
	
	/**
	 * 查看订单详情
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	Order findOne(String id) throws Exception;
	
	/**
	 * 查询买过的商品，通过订单状态查询订单，可以是：我预定的（state=0/1/2，buyerId=当前用户id），我买到的（state=3/4，buyerId=当前用户id），
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	List<Order> findByStateAsBuyer(int stateBegin, int stateEnd) throws Exception;
	
	/**
	 * 查询卖出的商品订单
	 * @return
	 * @throws Exception 
	 */
	List<Order> findByStateAsSeller() throws Exception;
	
	/**
	 * 买家删除任何状态的订单，终止本次交易。如果不是已成交/下架的商品（state==3/0），商品状态都恢复为售卖（state==1）
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	boolean deleteOrder(String id) throws Exception;
}
