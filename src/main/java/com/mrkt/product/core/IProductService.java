package com.mrkt.product.core;

import org.springframework.data.domain.Page;

import com.mrkt.product.model.Product;

/**
 * @ClassName	IProductService	
 * @Description
 * @author		hdonghong
 * @version 	v1.0 
 * @since		2018/02/16 14:18:44
 */
public interface IProductService {
	
	/**
	 * 通过id获取一条记录
	 * @param id
	 * @reDepturn
	 */
	Product findOne(Long id);
	
	/**
	 * 新增或修改记录保存
	 * 上架商品/修改商品信息
	 * @param entity
	 */
	void saveOrUpdate(Product entity);

	/**
	 * 根据类型、最新/最热、关键词搜索展示商品，
	 * @param currPage 当前页
	 * @param type 商品类型
	 * @param orderWay 排序方式，最新（默认）发布时间；最热，赞/浏览量？
	 * @param keywords 关键词
	 * @return
	 */
	Page<Product> findPage(int currPage, String type, String orderWay, String keywords);
	
	/**
	 * 商品下架，state，1->0
	 * @param id
	 */
	void cancel(Long id);
	
	/**
	 * 多个商品下架。state，1->0
	 * @param ids
	 */
	void cancelAll(Long[] ids);
	
	/**
	 * 删除此条商品记录
	 * @param entity
	 */
	void delete(Long id);
	
	/**
	 * 删除多条商品记录
	 * @param ids
	 */
	void deleteAll(Long[] ids);
	
	/**
	 * 点赞指定商品
	 * @param id
	 */
	void addLikes(Long id);
	
	/**
	 * 取消点赞
	 * @param id
	 */
	void removeLikes(Long id);
	
	/**
	 * 收藏指定商品
	 * @param id
	 */
	void addCollection(Long id);
	
	/**
	 * 取消收藏
	 * @param id
	 */
	void removeCollection(Long id);

	/**
	 * 添加商品留言
	 * @param productId
	 * @param commentContent
	 * @return
	 */
	public Product addComment(Long productId, String commentContent);
	
	/**
	 * 删除商品留言
	 * @param productId
	 * @param commentId
	 */
	public void removeComment(Long productId, Long commentId);
}
