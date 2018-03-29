package com.mrkt.product.dao;

import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mrkt.product.model.Order;

/**
 * @ClassName	OrderRepository
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/25 06:24:42
 */
@Repository
@Table(name="mrkt_pro_order")
@Qualifier("orderRepository")
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

	/**
	 * 查找买家的订单
	 * @param id
	 * @param buyerId
	 * @return
	 */
	Order findByIdAndBuyerId(String id, Long buyerId);
	
	/**
	 * 查找卖家的订单
	 * @param id
	 * @param buyerId
	 * @return
	 */
	Order findByIdAndSellerId(String id, Long sellerId);
	
	/**
	 * 通过商品编号查询到商品编号下的订单
	 * @param productId 商品编号
	 * @return
	 */
	List<Order> findByProductId(Long productId);
	
}
