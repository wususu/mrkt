package com.mrkt.product.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrkt.product.dao.ProductRepository;
import com.mrkt.product.model.Product;

@Service(value="productService")
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplates;
	
	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public Product findOne(Long id) {
		Product entity = productRepository.findOne(id);
		return entity;
	}
	
	@Override
	public void saveOrUpdate(Product entity){
		if (entity.getId() != null && entity.getId() >= 0) {
			// 更新商品信息
			entity.setTmUpdated(new Date());
		} else {
			// 发布商品
			entity.setState(1);// 状态初始化为发布
			entity.setCollection(0);// 收藏数为0
			entity.setLikes(0);// 点赞数为0
			entity.setTmCreated(new Date());
		}
		productRepository.save(entity);
	}
	
	@Override
	public Page<Product> findPage(int currPage, String type, String orderWay, String keywords) {
		final int pageSize = 10;
		
		Specification<Product> sp = (root, query, builder) -> {
				/*
	             * 构造断言
	             * @param root 实体对象引用
	             * @param query 规则查询对象
	             * @param builder 规则构建对象
	             * @return 断言
	             */
				List<Predicate> predicates = new ArrayList<>();
				if (type != null && type.trim().length() > 0) {
					predicates.add(builder.equal(root.get("ptype").as(String.class), type));
				}
				if (keywords != null && keywords.trim().length() > 0) {
					predicates.add(builder.like(root.get("name").as(String.class), "%" + keywords.trim() + "%"));
				}
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			};
		if (orderWay == null || orderWay.trim().length() <= 0) orderWay = "tmCreated";// 默认为最新排序
		Pageable pageable = new PageRequest(currPage, pageSize, new Sort(new Order(Direction.DESC, orderWay)));
		Page<Product> page = productRepository.findAll(sp, pageable);
		
		return page;
	}

	@Override
	public void cancel(Long id) {
		Product entity = productRepository.findOne(id);
		entity.setState(0);// 下架商品，将商品状态设为0
		productRepository.save(entity);
	}

	@Override
	public void cancelAll(Long[] ids) {
		for (Long id : ids) {
			this.cancel(id);
		}
	}

	@Override
	public void delete(Long id) {
		productRepository.delete(id);
	}

	@Override
	public void deleteAll(Long[] ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}

}
