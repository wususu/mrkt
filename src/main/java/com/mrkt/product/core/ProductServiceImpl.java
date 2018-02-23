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

import com.mrkt.product.dao.CommentRepository;
import com.mrkt.product.dao.ProductRepository;
import com.mrkt.product.model.Comment;
import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.dao.UserRepository;
import com.mrkt.usr.model.UserBase;

@Service(value="productService")
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Product findOne(Long id) {
		Product entity = productRepository.findOne(id);
		// 查询一次具体商品详情浏览量加1
		entity.setViews(entity.getViews()+1);
		entity = productRepository.save(entity);
		UserBase currUser = null;
		if ((currUser = ThisUser.get()) != null)
			entity.setIsLike(redisTemplate.boundSetOps("pro_like_" + id).
						isMember(currUser.getUid()));
		return entity;
	}
	
	@Override
	public void saveOrUpdate(Product entity){
		if (entity.getId() != null && entity.getId() >= 0) {
			Product po = productRepository.findOne(entity.getId());
			
			// 更新商品信息
			po.setName(entity.getName());
			po.setDesc(entity.getDesc());
			po.setPrice((entity.getPrice() != null) && (entity.getPrice() >= 0) ? entity.getPrice() : 0);
			po.setPtype(entity.getPtype());
			po.setCount(entity.getCount());
			po.setImages(entity.getImages());
			
			entity = po;
			entity.setTmUpdated(new Date());
			
		} else {
			// 发布商品，初始化基本信息
			entity.setState(1);// 状态为发布
			entity.setTmCreated(new Date());
			entity.setMrktUser(userRepository.findOne(entity.getMrktUser().getUid()));
		}
		productRepository.save(entity);
	}
	
	@SuppressWarnings("unchecked")
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
				predicates.add(builder.equal(root.get("state").as(Integer.class), 1));// 状态为1的商品，表示待售
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
		// 处理当前用户对于商品的点赞情况
		UserBase currUser = null;
		if ((currUser = ThisUser.get()) != null)
			for (Product product : page) {
				product.setIsLike(redisTemplate.boundSetOps("pro_like_" + product.getId()).
							isMember(currUser.getUid()));
			}
		
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

	@SuppressWarnings("unchecked")
	@Override
	public void addLikes(Long id) {
		try {
			Long result = redisTemplate.boundSetOps("pro_like_" + id).add(
					ThisUser.get().getUid());
			if (result == null || result == 0)
				throw new IllegalAccessException("用户已经点过赞");
			// 点赞数加1
			Product entity = productRepository.findOne(id);
			entity.setLikes(entity.getLikes() + 1);
			productRepository.saveAndFlush(entity);
		} catch (Exception e) {
			logger.info("用户已经点过赞");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeLikes(Long id) {
		try {
			Long result = redisTemplate.boundSetOps("pro_like_" + id).remove(
					ThisUser.get().getUid());
			if (result == null || result == 0)
				throw new IllegalAccessException("用户没有点赞过该商品");
			Product entity = productRepository.findOne(id);
			entity.setLikes(entity.getLikes() - 1);
			productRepository.saveAndFlush(entity);
		} catch (Exception e) {
			logger.info("用户没有点赞过该商品");
			e.printStackTrace();
		}
	}
	
	@Override
	public Product addComment(Long productId, String commentContent) {
		Product originalProduct = productRepository.findOne(productId);
		UserBase UserBase = ThisUser.get();
		Comment comment = new Comment(UserBase, commentContent);
		originalProduct.addComment(comment);
		return productRepository.save(originalProduct);
	}

	@Override
	public void removeComment(Long productId, Long commentId) {
		Product originalProduct = productRepository.findOne(productId);
		originalProduct.removeComment(commentId);
		productRepository.save(originalProduct);
	}

}
