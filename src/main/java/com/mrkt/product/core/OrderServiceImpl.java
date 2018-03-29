package com.mrkt.product.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mrkt.product.constant.OrderState;
import com.mrkt.product.constant.ProductState;
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
	public boolean requestOrder(Order order, Long productId) throws Exception {
		Product product = productRepository.findOne(productId);
		if (product.getState() != ProductState.ON_SALE.getState())
			throw new Exception("此商品已停止售卖");
		else if (ThisUser.get().getUid().equals(
				product.getMrktUser().getUid()))
			throw new Exception("买家与卖家不能是同一个人");
		
//		product.setState(ProductState.BE_ORDERED.getState());// 2表示商品被预定
		order.setProduct(product);
		order.setAmount(product.getPrice());
		// 设置卖家信息
		order.setSellerId(product.getMrktUser().getUid());
		order.setSellerName(product.getMrktUser().getnName());
		// 设置买家信息
		order.setBuyerId(ThisUser.get().getUid());
		order.setBuyerName(ThisUser.get().getnName());
		
//		productRepository.saveAndFlush(product);
		return orderRepository.saveAndFlush(order) != null;
	}

	@Override
	public boolean processOrder(String id, int state) throws Exception {
		Order order = orderRepository.findByIdAndSellerId(id, ThisUser.get().getUid());
		if (state == OrderState.BE_CANCELED.getState()) {// 卖家取消订单或拒绝预定，无操作
//			product.setState(ProductState.ON_SALE.getState());// 恢复售卖状态
		} else if (state == OrderState.BE_WAITING_PYAMENT.getState()) {// 卖家接受预定请求，修改商品状态为被预定，同时拒绝其它预定
			// 修改商品状态
			Product product = order.getProduct();
			product.setState(ProductState.BE_ORDERED.getState());
			productRepository.saveAndFlush(product);
			// 需要拒绝其它请求
			List<Order> orders = orderRepository.findByProductId(product.getId());
			if (orders != null && orders.size() > 1) {
				orders.forEach(o -> {
					if (!o.getId().equals(id)) {
						o.setState(OrderState.BE_CANCELED.getState());
						orderRepository.save(order);
					}
				});
			}
		}
		// 修改订单状态
		order.setState(state);
		orderRepository.saveAndFlush(order);
		return true;
	}

	@Override
	public boolean submitOrder(Order order) throws Exception{
		Order entity = orderRepository.findByIdAndBuyerId(order.getId(), ThisUser.get().getUid());
		
		if (!entity.getState().equals(OrderState.BE_WAITING_PYAMENT.getState())) {
			throw new Exception("非法操作不是'待支付，需完善收货人信息'状态的订单");
		}
		if (order.getBuyerName() != null && order.getBuyerName().length() > 0) 
			entity.setBuyerName(order.getBuyerName());
		entity.setBuyerPhone(order.getBuyerPhone());
		entity.setBuyerWx(order.getBuyerWx());
		entity.setAddress(order.getAddress());
		entity.setState(OrderState.BE_WAITING_RECEIVING.getState());// 3表示订单状态变为待收货
		entity.setCreateTime(new Date());
		
		orderRepository.saveAndFlush(entity);
		return true;
	}

	@Override
	public boolean endOrder(String id) throws Exception {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			Product product = order.getProduct();
			if (product.getState() != ProductState.BE_ORDERED.getState() || 
				order.getState() != OrderState.BE_WAITING_RECEIVING.getState()) {// 不处于被预定的商品或不处于待收货状态的订单
				throw new Exception("非法操作不是被预定的商品或不处于待收货状态的订单");
			}
			product.setState(ProductState.BE_SOLD.getState());// 商品3已售出
			order.setState(OrderState.BE_COMMENTING.getState());// 待评论
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
	public boolean commentSeller(String id, int score, String comment) throws Exception {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			if (order.getState() != OrderState.BE_COMMENTING.getState())
				throw new Exception("交易尚未完成，无法评价");
			order.setSellerScore(score);
			order.setSellerComment(comment);
			// 若双方都互评了，则修改订单状态为交易完成
			if (order.getBuyerComment() != null && !"".equals(order.getBuyerComment())) {
				order.setState(OrderState.BE_COMPLETE.getState());
			}
			
			orderRepository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean commentBuyer(String id, int score, String comment) throws Exception {
		try {
			Order order = orderRepository.findByIdAndSellerId(id, ThisUser.get().getUid());
			if (order.getState() != OrderState.BE_COMMENTING.getState())
				throw new  Exception("交易尚未完成，无法评价");
			order.setBuyerScore(score);
			order.setBuyerComment(comment);
			// 若双方都互评了，则修改订单状态为交易完成
			if (order.getSellerComment() != null && !"".equals(order.getSellerComment())) {
				order.setState(OrderState.BE_COMPLETE.getState());
			}
			orderRepository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Order findOne(String id) throws Exception {
		return orderRepository.findOne(id);
	}

	@Override
	public List<Order> findByStateAsBuyer(int stateBegin, int stateEnd) throws Exception {
		Specification<Order> sp = (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.between(root.get("state").as(Integer.class), stateBegin, stateEnd));
			predicates.add(builder.equal(root.get("buyerId").as(String.class), ThisUser.get().getUid()));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return orderRepository.findAll(sp);
	}

	@Override
	public List<Order> findByStateAsSeller() throws Exception {
		Specification<Order> sp = (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.notEqual(root.get("state").as(Integer.class), OrderState.BE_CANCELED.getState()));
			predicates.add(builder.equal(root.get("sellerId").as(String.class), ThisUser.get().getUid()));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return orderRepository.findAll(sp);
	}

	@Override
	public boolean deleteOrder(String id) throws Exception {
		try {
			Order order = orderRepository.findByIdAndBuyerId(id, ThisUser.get().getUid());
			if (order == null)
				throw new Exception("不存在的订单");
			
			// 删除订单前处理商品状态，
			// 订单状态0|1， 2|3(商品状态：被预定)， 4|5(商品状态：已售出)
			// 删除订单状态为2|3的订单需要修改商品为“售卖中”
			Product product = order.getProduct();
			if (order.getState() == OrderState.BE_WAITING_PYAMENT.getState() ||
				order.getState() == OrderState.BE_WAITING_RECEIVING.getState()) {
				// 删除订单，商品还原回到售卖状态
				product.setState(ProductState.ON_SALE.getState());
				productRepository.save(product);
			}
			order.setState(OrderState.BE_DELETED.getState());
			orderRepository.save(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
