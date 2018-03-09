package com.mrkt.product.core;

import java.util.List;

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
	 * @throws Exception 
	 * @reDepturn
	 */
	Product findOne(Long id) throws Exception;
	
	/**
	 * 新增或修改记录保存
	 * 上架商品/修改商品信息
	 * @param entity
	 * @throws Exception 
	 */
	void saveOrUpdate(Product entity) throws Exception;

	/**
	 * 根据类型、最新/最热、关键词搜索展示商品，
	 * @param currPage 当前页
	 * @param type 商品类型
	 * @param orderWay 排序方式，最新（默认）发布时间；最热，赞/浏览量？
	 * @param keywords 关键词
	 * @return
	 * @throws Exception 
	 */
	Page<Product> findPage(int currPage, String type, String orderWay, String keywords) throws Exception;
	
	/**
	 * 商品下架，state，1->0
	 * @param id
	 * @throws Exception 
	 */
	void cancel(Long id) throws Exception;
	
	/**
	 * 多个商品下架。state，1->0
	 * @param ids
	 * @throws Exception 
	 */
	void cancelAll(Long[] ids) throws Exception;
	
	/**
	 * 删除此条商品记录
	 * @param entity
	 * @throws Exception 
	 */
	void delete(Long id) throws Exception;
	
	/**
	 * 删除多条商品记录
	 * @param ids
	 * @throws Exception 
	 */
	void deleteAll(Long[] ids) throws Exception;
	
	/**
	 * 点赞指定商品
	 * @param id
	 * @throws Exception 
	 */
	void addLikes(Long id) throws Exception;
	
	/**
	 * 取消点赞
	 * @param id
	 * @throws Exception 
	 */
	void removeLikes(Long id) throws Exception;
	
	/**
	 * 收藏指定商品
	 * @param id
	 * @throws Exception 
	 */
	void addCollection(Long id) throws Exception;
	
	/**
	 * 取消收藏
	 * @param id
	 * @throws Exception 
	 */
	void removeCollection(Long id) throws Exception;

	/**
	 * 添加商品留言
	 * @param productId
	 * @param commentContent
	 * @return
	 * @throws Exception 
	 */
	Product addComment(Long productId, String commentContent) throws Exception;
	
	/**
	 * 删除商品留言
	 * @param productId
	 * @param commentId
	 * @throws Exception 
	 */
	void removeComment(Long productId, Long commentId) throws Exception;

	/**
	 * 获取我发布的商品，product.state=1
	 * @return
	 * @throws Exception 
	 */
	List<Product> getMine() throws Exception;

	List<Product> getCollection() throws Exception;
}
