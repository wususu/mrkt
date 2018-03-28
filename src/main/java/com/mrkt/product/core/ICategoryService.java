package com.mrkt.product.core;

import java.util.List;

import com.mrkt.product.model.Category;

/**
 * @ClassName	ICategoryService
 * @Description 商品分类的服务层接口
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 14:58:18
 */
public interface ICategoryService {

	/**
	 * 获取所有商品分类
	 * @return
	 */
	List<Category> findAll();
}
