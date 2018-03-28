package com.mrkt.product.core;

import com.mrkt.product.model.Comment;

/**
 * @ClassName	ICommentService
 * @Description 商品留言的服务接口
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/23 16:27:10
 */
public interface ICommentService {

	/**
	 * 删除留言
	 * @param id
	 * @throws Exception 
	 */
	void removeComment(Long id) throws Exception;
	
	/**
	 * 通过id获取一条留言
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	Comment getCommentById(Long id) throws Exception;
}
