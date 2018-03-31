package com.mrkt.product.core;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrkt.product.dao.CategoryRepository;
import com.mrkt.product.model.Category;

/**
 * @ClassName	CategoryServiceImpl
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 16:17:56
 */
@Service(value="categoryService")
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll() {
		List<Category> categoryList = (List<Category>) redisTemplate.boundValueOps("pro_category").get();

		if (categoryList == null) {// 缓存中没有数据，查询后添加到缓存
			logger.info("缓存中没有数据，重新查询商品分类并添加到缓存");
			categoryList = categoryRepository.findAll();
			redisTemplate.boundValueOps("pro_category").set(categoryList);
			if (!redisTemplate.boundListOps("pro_category").expire(10, TimeUnit.DAYS)) {
				logger.warn("设置缓存时间失败");
			}
		}
		return categoryList;
	}

}
