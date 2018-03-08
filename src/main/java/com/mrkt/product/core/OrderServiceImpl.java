package com.mrkt.product.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mrkt.product.dao.OrderRepository;
import com.mrkt.product.dao.ProductRepository;
import com.mrkt.product.model.Order;
import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;

/**
 * @ClassName	OrderServiceImpl
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/25 13:29:45
 */
@Service(value="orderService")
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public boolean requestOrder(Order order, Long productId) {
		try {
			Product product = productRepository.findOne(productId);
			if (product.getState() != 1)
				throw new IllegalAccessException("此商品已停止售卖");
			else if (ThisUser.get().getUid().equals(
					product.getMrktUser().getUid()))
				throw new IllegalAccessException("买家与卖家不能是同一个人");
			
			product.setState(2);// 2表示商品被预定
			order.setProduct(product);
			order.setAmount(product.getPrice());
			// 设置卖家信息
			order.setSellerId(product.getMrktUser().getUid());
			order.setSellerName(product.getMrktUser().getnName());
			// 设置买家信息
			order.setBuyerId(ThisUser.get().getUid());
			order.setBuyerName(ThisUser.get().getnName());
			
			productRepository.saveAndFlush(product);
			return orderRepository.saveAndFlush(order) != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean processOrder(String id, int state) {
		Order order = orderRepository.findByIdAndSellerId(id, ThisUser.get().getUid());
		if (state == 0) {// 卖家取消订单
			Product product = order.getProduct();
			product.setState(1);// 恢复售卖状态
			productRepository.saveAndFlush(product);
		}
		order.setState(state);
		orderRepository.saveAndFlush(order);
		return true;
	}

	@Override
	public boolean submitOrder(Order order) {
		Order entity = orderRepository.findByIdAndBuyerId(order.getId(), ThisUser.get().getUid());
		
		if (order.getBuyerName() != null && order.getBuyerName().length() > 0) 
			entity.setBuyerName(order.getBuyerName());
		entity.setBuyerPhone(order.getBuyerPhone());
		entity.setBuyerWx(order.getBuyerWx());
		entity.setAddress(order.getAddress());
		entity.setState(3);// 3表示订单状态变为待收货
		entity.setCreateTime(new Date());
		
		orderRepository.saveAndFlush(entity);
		return true;
	}

	@Override
	public boolean endOrder(String id) {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			Product product = order.getProduct();
			if (product.getState() != 2 || order.getState() != 3) // 不处于被预定的商品或不处于待收货状态的订单
				throw new IllegalAccessException("非法操作不是被预定的商品或不处于待收货状态的订单");
			product.setState(3);// 商品3已售出
			order.setState(4);
			order.setEndTime(new Date());
			productRepository.saveAndFlush(product);
			orderRepository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean commentSeller(String id, int score, String comment) {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			if (order.getState() != 4)
				throw new IllegalAccessException("交易尚未完成，无法评价");
			order.setSellerScore(score);
			order.setSellerComment(comment);
			orderRepository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean commentBuyer(String id, int score, String comment) {
		try {
			Order order = orderRepository.findByIdAndSellerId(id, ThisUser.get().getUid());
			if (order.getState() != 4)
				throw new IllegalAccessException("交易尚未完成，无法评价");
			order.setBuyerScore(score);
			order.setBuyerComment(comment);
			orderRepository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Order findOne(String id) {
		return orderRepository.findOne(id);
	}

	@Override
	public List<Order> findByStateAsBuyer(int stateBegin, int stateEnd) {
		Specification<Order> sp = (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.between(root.get("state").as(Integer.class), stateBegin, stateEnd));
			predicates.add(builder.equal(root.get("buyerId").as(String.class), ThisUser.get().getUid()));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return orderRepository.findAll(sp);
	}

	@Override
	public List<Order> findByStateAsSeller() {
		Specification<Order> sp = (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.notEqual(root.get("state").as(Integer.class), 0));
			predicates.add(builder.equal(root.get("sellerId").as(String.class), ThisUser.get().getUid()));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return orderRepository.findAll(sp);
	}

	@Override
	public boolean deleteOrder(String id) {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			if (order == null)
				throw new IllegalAccessException("不存在的订单");
			Product product = order.getProduct();
			if (product.getState() != 3 && product.getState() != 0) {
				product.setState(1);
				productRepository.save(product);
			}
//			orderRepository.delete(order);
			order.setState(-1);
			orderRepository.save(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
